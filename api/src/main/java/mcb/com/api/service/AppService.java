package mcb.com.api.service;

import mcb.com.domain.dto.request.ValidateSignatureRequest;
import mcb.com.domain.dto.response.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AppService {
    ResponseEntity<ApiResponse<List<EventSourceResponse>>> listEvents(int page, int size);
    ResponseEntity<ApiResponse<List<UsersResponse>>> listUsers(int page, int size);
    ResponseEntity<ApiResponse<String>> retrieveSignatureInPdf(UUID eventPid);
    ResponseEntity<ApiResponse<SignatureValidationResponse>> retrieveSignatureInPdf(ValidateSignatureRequest payload);
    ResponseEntity<ApiResponse<List<String>>> listCurrency();
    ResponseEntity<ApiResponse<List<EventSourceSummaryResponse>>> listEventSummary();

}