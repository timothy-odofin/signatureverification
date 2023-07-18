package mcb.com.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoles extends BaseEntity {
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne
    private Roles role;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private Users user;
}
