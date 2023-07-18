package mcb.com.api.service;

import mcb.com.domain.dto.request.ValidateSignatureRequest;
import mcb.com.domain.dto.response.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AppService {
    ResponseEntity<ApiResponse<List<EventSourceResponse>>> listEvents(int page, int size);
    ResponseEntity<ApiResponse<List<UsersResponse>>> listUsers(int page, int size);
    ResponseEntity<ApiResponse<String>> retrieveSignatureInPdf(UUID eventPid);
    ResponseEntity<ApiResponse<SignatureValidationResponse>> validateSignature(ValidateSignatureRequest payload);
    ResponseEntity<ApiResponse<Set<String>>> listCurrency();
    ResponseEntity<ApiResponse<List<EventSourceSummaryResponse>>> listEventSummary();

}
