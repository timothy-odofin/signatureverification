package mcb.com.domain.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoles extends BaseEntity {
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    @ManyToOne
    private Roles role;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne
    private Users user;
}
