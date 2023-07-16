package mcb.com.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Roles extends BaseEntity{
    private String roleName;
    private String description;
}
