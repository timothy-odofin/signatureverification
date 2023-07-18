package mcb.com.persistence;

import mcb.com.domain.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Roles, Long> {
}
