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
public class EventSourceUpdateRequest {
    private String comments;
    private String transactionCurrency;
    private double transactionAmount;
    private double amountInMur;
    private String debitAccountNumber;
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
