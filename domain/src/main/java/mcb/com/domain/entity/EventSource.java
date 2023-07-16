package mcb.com.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "EVENT_SOURCE")
@Data
@EqualsAndHashCode(callSuper = true)
public class EventSource  extends BaseEntity{

    @Column(name = "business_key", nullable = false)
    private String businessKey;

    @Column(name = "application", nullable = false)
    private String application;
    private String comments;
    @Column(name = "transaction_currency", nullable = false)
    private String transactionCurrency;

    @Column(name = "transaction_amount", nullable = false)
    private String transactionAmount;

    @Column(name = "amount_in_mur", nullable = false)
    private String amountInMur;

    @Column(name = "debit_account_number", nullable = false)
    private String debitAccountNumber;
    private String accountShortName;
    @Column(name = "debit_account_ccy", nullable = false)
    private String debitAccountCcy;
    private String paymentDetails1;
    private String paymentDetails2;
    private String paymentDetails3;
    private String paymentDetails4;
    private String verified;
    @Column(name = "discrepancy_reason")
    private String discrepancyReason;

    @Column(name = "created_by", updatable = false, nullable = false, columnDefinition =
            "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID createdBy;

    @Column(name = "updated_by", updatable = false, nullable = false, columnDefinition =
            "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID updatedBy;

    @Column(nullable = false)
    private String priority;
    @Column(name = "source_bu")
    private String sourceBU;

    @Column(name = "document_capture_reference")
    private String documentCaptureReference;

    private String status;
}
