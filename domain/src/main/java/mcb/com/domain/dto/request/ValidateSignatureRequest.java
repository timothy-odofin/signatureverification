package mcb.com.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateSignatureRequest {
    @NotNull
    private UUID eventSourcePid;
    @NotNull
    @NotEmpty(message = "Invalid account number")
    private String eventAccountNumber;
}
