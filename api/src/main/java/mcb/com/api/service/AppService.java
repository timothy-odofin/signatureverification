package mcb.com.api.service;

import mcb.com.domain.dto.request.ValidateSignatureRequest;
import mcb.com.domain.dto.response.ApiResponse;
import mcb.com.domain.dto.response.SignatureValidationResponse;
import mcb.com.domain.dto.response.UsersResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AppService {
    ResponseEntity<ApiResponse<List<UsersResponse>>> listEvents(int page, int size);
    ResponseEntity<ApiResponse<List<UsersResponse>>> listUsers(int page, int size);
    ResponseEntity<ApiResponse<String>> retrieveSignatureInPdf(UUID eventPid);
    ResponseEntity<ApiResponse<SignatureValidationResponse>> retrieveSignatureInPdf(ValidateSignatureRequest payload);

}
