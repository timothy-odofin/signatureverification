package mcb.com.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ValidateSignatureRequest {
    @NotNull
    private UUID eventSourcePid;
    @NotNull
    @NotEmpty(message = "Invalid account number")
    private String eventAccountNumber;
}
