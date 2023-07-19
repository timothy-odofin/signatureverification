package mcb.com.api.mapper;

import mcb.com.domain.dto.request.EventSourceUpdateRequest;
import mcb.com.domain.entity.EventSource;

import java.time.LocalDateTime;

public class DtoMapper {
    public static void mapToEventSource(EventSource mapTo, EventSourceUpdateRequest mapFrom){
        mapTo.setVerified(mapFrom.getVerified());
        mapTo.setAmountInMur(mapFrom.getAmountInMur()==null?mapTo.getAmountInMur():mapFrom.getAmountInMur());
        mapTo.setComments(mapFrom.getComments() !=null? mapFrom.getComments() : mapTo.getComments());
        mapTo.setDebitAccountCcy(mapFrom.getDebitAccountCcy() !=null? mapFrom.getDebitAccountCcy() : mapTo.getDebitAccountCcy());
        mapTo.setDebitAccountNumber(mapFrom.getDebitAccountNumber()!=null? mapFrom.getDebitAccountNumber() : mapTo.getDebitAccountNumber());
        mapTo.setDiscrepancyReason(mapFrom.getDiscrepancyReason());
        mapTo.setPaymentDetails1(mapFrom.getPaymentDetails1() !=null? mapFrom.getPaymentDetails1() : mapTo.getPaymentDetails1());
        mapTo.setPaymentDetails2(mapFrom.getPaymentDetails2() !=null? mapFrom.getPaymentDetails2() : mapTo.getPaymentDetails2());
        mapTo.setPaymentDetails3(mapFrom.getPaymentDetails3() !=null? mapFrom.getPaymentDetails3() : mapTo.getPaymentDetails3());
        mapTo.setPaymentDetails4(mapFrom.getPaymentDetails3() !=null? mapFrom.getPaymentDetails4() : mapTo.getPaymentDetails4());
        mapTo.setUpdatedOn(LocalDateTime.now());
        mapTo.setStatus(mapFrom.getActionStatus().name());
    }
}
