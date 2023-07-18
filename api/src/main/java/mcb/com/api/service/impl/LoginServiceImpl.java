package mcb.com.api.service.impl;

import lombok.RequiredArgsConstructor;
import mcb.com.api.mapper.Mapper;
import mcb.com.api.security.TokenProvider;
import mcb.com.api.service.LoginService;
import mcb.com.api.service.RefreshTokenService;
import mcb.com.api.utils.MessageUtil;
import mcb.com.domain.dto.request.Login;
import mcb.com.domain.dto.response.ApiResponse;
import mcb.com.domain.dto.response.AuthTokenInfo;
import mcb.com.domain.dto.response.LoginResponse;
import mcb.com.domain.dto.response.exception.UserNotFoundException;
import mcb.com.domain.entity.RefreshToken;
import mcb.com.domain.entity.Users;
import mcb.com.persistence.UsersRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static mcb.com.api.utils.MessageUtil.SUCCESS;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UsersRepo usersRepo;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.token.prefix}")
    private String tokenType;

    @Override
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid Login payload) {
        Users existingUser = usersRepo.findByUsername(payload.getUsername()).orElseThrow(()->new UserNotFoundException(MessageUtil.USER_NOT_FOUND));
        if(!passwordEncoder.matches(payload.getPassword(), existingUser.getPassword()))
            throw new  UserNotFoundException(MessageUtil.USER_NOT_FOUND);

        LoginResponse loginResponse = Mapper.convertObject(existingUser, LoginResponse.class);
        loginResponse.setToken(obtainAccessToken(payload.getUsername(),payload.getPassword()));
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, HttpStatus.OK.value(), loginResponse));
    }

    private AuthTokenInfo obtainAccessToken(String username, String password) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);
        AuthTokenInfo userToken = new AuthTokenInfo();
        userToken.setAccess_token(token);
        userToken.setScope("read");
        userToken.setToken_type(tokenType);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(username);
        userToken.setRefresh_token(refreshToken.getToken());
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        userToken.setRoles(roles);
        return userToken;
    }
}
