package mcb.com.api.service.impl;

import lombok.RequiredArgsConstructor;
import mcb.com.api.service.AppService;
import mcb.com.domain.dto.request.ValidateSignatureRequest;
import mcb.com.domain.dto.response.*;
import mcb.com.persistence.EventSourceRepo;
import mcb.com.persistence.EventSourceSummaryRepo;
import mcb.com.persistence.UsersRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {
    private final UsersRepo usersRepo;
    private final EventSourceRepo eventSourceRepo;
    private final EventSourceSummaryRepo eventSourceSummaryRepo;

    @Override
    public ResponseEntity<ApiResponse<List<EventSourceResponse>>> listEvents(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<UsersResponse>>> listUsers(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<String>> retrieveSignatureInPdf(UUID eventPid) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<SignatureValidationResponse>> retrieveSignatureInPdf(ValidateSignatureRequest payload) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<String>>> listCurrency() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<EventSourceSummaryResponse>>> listEventSummary() {
        return null;
    }
}
