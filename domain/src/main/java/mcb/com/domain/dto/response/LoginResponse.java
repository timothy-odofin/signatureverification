package mcb.com.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	private String pid;
	private String firstName;
	private String lastName;
	private String businessUnit;
	AuthTokenInfo token;


}
