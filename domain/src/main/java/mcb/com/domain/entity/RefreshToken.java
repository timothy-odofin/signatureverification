package mcb.com.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

import javax.persistence.*;
@Entity(name = "refresh_token")
@Data
@EqualsAndHashCode(callSuper = true)
public class RefreshToken extends BaseEntity {

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private Users user;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private Instant expiryDate;


}
