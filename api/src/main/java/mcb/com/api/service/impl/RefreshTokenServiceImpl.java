package mcb.com.api.service.impl;

import lombok.RequiredArgsConstructor;
import mcb.com.api.service.RefreshTokenService;
import mcb.com.api.utils.MessageUtil;
import mcb.com.domain.dto.response.exception.BadRequestException;
import mcb.com.domain.dto.response.exception.UserNotFoundException;
import mcb.com.domain.entity.RefreshToken;
import mcb.com.domain.entity.Users;
import mcb.com.persistence.RefreshTokenRepo;
import mcb.com.persistence.UsersRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${jwt.refreshExpirationDateInMs}")
    public long REFRESH_TOKEN_VALIDITY;

    private final RefreshTokenRepo refreshTokenRepo;


    private final UsersRepo usersRepo;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();
        Users user = usersRepo.findByUsername(userId).orElseThrow(()->new UserNotFoundException(MessageUtil.USER_NOT_FOUND));
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plus(REFRESH_TOKEN_VALIDITY, ChronoUnit.HOURS));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setPid(UUID.randomUUID());
        Optional<RefreshToken> refreshToken1 = refreshTokenRepo.findByUser(user);
        RefreshToken dbToken = null;
        if (refreshToken1.isPresent()) {
            dbToken = refreshToken1.get();
            refreshToken.setId(dbToken.getId());

            if(!(dbToken.getExpiryDate().compareTo(Instant.now()) < 0))
                refreshToken =dbToken;
        }else{
            refreshToken = refreshTokenRepo.save(refreshToken);

        }

        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepo.delete(token);
            throw new BadRequestException(MessageUtil.INVALID_JWT_TOKEN);
        }

        return token;
    }

}
