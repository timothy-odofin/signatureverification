package mcb.com.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventSourceUpdateRequest {
    private String comments;
    @NotNull
    @NotEmpty
    private String transactionCurrency;
    @Min(value=1)
    private double transactionAmount;
    @Min(value=1)
    private double amountInMur;
    @NotNull
    @NotEmpty
    private String debitAccountNumber;
    @NotNull
    @NotEmpty
    private String debitAccountCcy;
    private String paymentDetails1;
    private String paymentDetails2;
    private String paymentDetails3;
    private String paymentDetails4;
    @NotNull
    @NotEmpty
    private String verified;
    @NotNull
    @NotEmpty
    private String discrepancyReason;
    @NotNull
    private StatusEnum actionStatus;

}
