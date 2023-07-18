package mcb.com.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Login {
    @NotNull
    @NotEmpty(message = "Invalid username")
    private String username;
    @NotNull
    @NotEmpty(message = "invalid password")
    private String password;
}
