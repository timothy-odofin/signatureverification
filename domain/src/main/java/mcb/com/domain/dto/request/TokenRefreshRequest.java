package mcb.com.domain.dto.request;

import lombok.Data;

@Data
public class TokenRefreshRequest {
  private String refreshToken;

}
