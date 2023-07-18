package mcb.com.api.security;

import mcb.com.domain.entity.UserRoles;
import mcb.com.domain.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String password;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final UUID pid;

    public CustomUserDetails(Users user, List<UserRoles> list) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.pid=user.getPid();
        this.authorities = translate(list);
    }

    private Collection<? extends GrantedAuthority> translate(List<UserRoles> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UserRoles role : roles) {
            String name = role.getRole().getRoleName().toUpperCase();
                name = "ROLE_" + name; // ROLE_USER
            grantedAuthorities.add(new SimpleGrantedAuthority(name));
        }
        return grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UUID getPid() {
        return pid;
    }
}