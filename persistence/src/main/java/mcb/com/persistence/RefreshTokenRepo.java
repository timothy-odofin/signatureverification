package mcb.com.persistence;

import mcb.com.domain.entity.RefreshToken;
import mcb.com.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);
  @Modifying
  int deleteByUser(Users user);
  Optional<RefreshToken> findByUser(Users user);
}
