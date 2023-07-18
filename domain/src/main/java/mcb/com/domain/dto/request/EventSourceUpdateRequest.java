package mcb.com.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EventSourceUpdateRequest {
    private String comments;
    @NotNull
    @NotEmpty
    private String transactionCurrency;
    @Min(value=1)
    private Double transactionAmount;
    @Min(value=1)
    private Double amountInMur;
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
