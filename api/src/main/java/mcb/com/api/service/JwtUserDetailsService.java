package mcb.com.api.service;

import lombok.RequiredArgsConstructor;
import mcb.com.api.security.CustomUserDetails;
import mcb.com.api.utils.MessageUtil;
import mcb.com.domain.dto.response.exception.UserNotFoundException;
import mcb.com.domain.entity.Users;
import mcb.com.persistence.UserRolesRepo;
import mcb.com.persistence.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UsersRepo usersRepo;
    private final UserRolesRepo userRolesRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user = usersRepo.findByUsername(userName).orElseThrow(()->new UserNotFoundException(MessageUtil.USER_NOT_FOUND));
        return new CustomUserDetails(user, userRolesRepo.findByUserId(user.getId()));
    }

}
