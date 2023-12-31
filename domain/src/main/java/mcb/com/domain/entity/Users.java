package mcb.com.domain.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String businessUnit;
    private Boolean active;
    private String accountStatus;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoles> userRoles;
}
