package mcb.com.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private String pid;
	private String firstName;
	private String lastName;
	private String businessUnit;
	private List<String> roles;

}
