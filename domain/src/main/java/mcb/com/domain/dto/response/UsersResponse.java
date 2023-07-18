package mcb.com.domain.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UsersResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String businessUnit;
    private Boolean active;
    private String accountStatus;
    private UUID pid;
}
