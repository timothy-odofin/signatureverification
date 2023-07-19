package mcb.com.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mcb.com.api.security.CustomUserDetails;
import mcb.com.api.utils.MessageUtil;
import mcb.com.domain.dto.response.exception.UserNotFoundException;
import mcb.com.domain.entity.UserRoles;
import mcb.com.domain.entity.Users;
import mcb.com.persistence.UserRolesRepo;
import mcb.com.persistence.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UsersRepo usersRepo;
    private final UserRolesRepo userRolesRepo;

    /**
     * Loads the user details for the specified username.
     *
     * @param userName the username of the user to load.
     * @return the UserDetails object containing the user details.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // Find the user in the database by the provided username
        Users user = usersRepo.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(MessageUtil.USER_NOT_FOUND));

        // Retrieve the roles associated with the user
        List<UserRoles> userRoles = userRolesRepo.findByUserId(user.getId());

        // Create and return a CustomUserDetails object that implements the UserDetails interface
        // This object contains the user's information and authorities (roles)
        return new CustomUserDetails(user, userRoles);
    }

}
