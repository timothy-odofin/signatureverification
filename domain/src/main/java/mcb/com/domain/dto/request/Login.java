package mcb.com.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Login {
    @NotNull
    @NotEmpty(message = "Invalid username")
    private String username;
    @NotNull
    @NotEmpty(message = "invalid password")
    private String password;
}
