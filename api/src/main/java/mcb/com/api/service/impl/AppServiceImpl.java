package mcb.com.api.service.impl;

import lombok.RequiredArgsConstructor;
import mcb.com.api.mapper.Mapper;
import mcb.com.api.service.AppService;
import mcb.com.api.utils.MessageUtil;
import mcb.com.common.SigUtils;
import mcb.com.common.SignatureValidationInternal;
import mcb.com.domain.dto.request.ValidateSignatureRequest;
import mcb.com.domain.dto.response.*;
import mcb.com.domain.dto.response.exception.RecordNotFounException;
import mcb.com.domain.entity.EventSource;
import mcb.com.domain.entity.Users;
import mcb.com.persistence.EventSourceRepo;
import mcb.com.persistence.EventSourceSummaryRepo;
import mcb.com.persistence.UsersRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static mcb.com.api.utils.Constant.*;
import static mcb.com.api.utils.MessageUtil.*;

@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {
    private final UsersRepo usersRepo;
    private final EventSourceRepo eventSourceRepo;
    private final EventSourceSummaryRepo eventSourceSummaryRepo;

    @Override
    public ResponseEntity<ApiResponse<List<EventSourceResponse>>> listEvents(int page, int size) {
        Page<EventSource> eventSourcePage = eventSourceRepo.findAll(PageRequest.of(page,size));

        return ResponseEntity.ok(new ApiResponse<>(MessageUtil.SUCCESS, HttpStatus.OK.value(),
                Mapper.convertList(eventSourcePage.toList(), EventSourceResponse.class))
                .addMeta("totalElement", eventSourcePage.getTotalElements())
                .addMeta("totalPage", eventSourcePage.getTotalPages()));
    }
private EventSource findByPid(UUID eventPid){
        return  eventSourceRepo.findByPid(eventPid).orElseThrow(()->new RecordNotFounException(RECORD_NOT_FOUND));

}
    @Override
    public ResponseEntity<ApiResponse<List<UsersResponse>>> listUsers(int page, int size) {
        Page<Users> eventSourcePage = usersRepo.findAll(PageRequest.of(page,size));

        return ResponseEntity.ok(new ApiResponse<>(MessageUtil.SUCCESS, HttpStatus.OK.value(),
                Mapper.convertList(eventSourcePage.toList(), UsersResponse.class))
                .addMeta("totalElement", eventSourcePage.getTotalElements())
                .addMeta("totalPage", eventSourcePage.getTotalPages()));
    }

    @Override
    public ResponseEntity<ApiResponse<String>> retrieveSignatureInPdf(UUID eventPid) {
        EventSource eventSource =findByPid(eventPid);
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS,HttpStatus.OK.value(), SigUtils.generateBase64PdfSignature(eventSource.getDebitAccountNumber())));
    }

    @Override
    public ResponseEntity<ApiResponse<SignatureValidationResponse>> validateSignature(ValidateSignatureRequest payload) {
        EventSource eventSource =findByPid(payload.getEventSourcePid());
        SignatureValidationInternal validSign =SigUtils.compareSignatures(eventSource.getDebitAccountNumber(), payload.getEventAccountNumber());
        eventSource.setVerified(validSign.isValid()?YES:NO);
        eventSourceRepo.save(eventSource);
        return ResponseEntity.ok(new ApiResponse<>(validSign.isValid()?SUCCESS:FAIL,HttpStatus.OK.value(),
                SignatureValidationResponse.builder()
                        .signImage(validSign.getSignImage())
                        .status(validSign.isValid()? SIGNATURE_VALIDATION_SUCCESSFUL:SIGNATURE_VALIDATION_FAIL)
                        .build()));
    }

    @Override
    public ResponseEntity<ApiResponse<Set<String>>> listCurrency() {
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS,HttpStatus.OK.value(),eventSourceRepo.listCurrencies()));
    }

    @Override
    public ResponseEntity<ApiResponse<List<EventSourceSummaryResponse>>> listEventSummary() {
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS,HttpStatus.OK.value(), eventSourceSummaryRepo.getEventSourceSummary()));

    }
}
