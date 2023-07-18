package mcb.com.persistence;

import mcb.com.domain.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRolesRepo extends JpaRepository<UserRoles,Long> {
    @Query("select rs from UserRoles rs where rs.user.id=:userId")
    List<UserRoles> findByUserId(@Param("userId") Long userId);
    @Query("select rs from UserRoles rs where rs.user.username=:userName")
    List<UserRoles> findByUserName(@Param("userName") String userName);
}
