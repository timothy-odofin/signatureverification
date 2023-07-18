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
    public Roles(){
        super();
    }
    public Roles(Long id){
        super();
        this.id= id;

    }

}
