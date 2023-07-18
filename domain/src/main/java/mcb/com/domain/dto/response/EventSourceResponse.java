package mcb.com.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventSourceResponse {

    private String businessKey;
    private String application;
    private String comments;
    private String transactionCurrency;
    private Double transactionAmount;
    private Double amountInMur;
    private String debitAccountNumber;
    private String accountShortName;
    private String debitAccountCcy;
    private String paymentDetails1;
    private String paymentDetails2;
    private String paymentDetails3;
    private String paymentDetails4;
    private String verified;
    private String discrepancyReason;
    private UUID createdBy;
    private UUID updatedBy;
    private String priority;
    private String sourceBU;
    private String documentCaptureReference;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedOn;
    private UUID pid;
}
