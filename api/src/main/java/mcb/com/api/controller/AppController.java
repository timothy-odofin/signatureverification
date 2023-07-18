package mcb.com.api.controller;

import static mcb.com.api.utils.ApiPath.*;
import static mcb.com.api.utils.Constant.*;

import lombok.RequiredArgsConstructor;
import mcb.com.api.service.AppService;
import mcb.com.domain.dto.request.EventSourceUpdateRequest;
import mcb.com.domain.dto.request.ValidateSignatureRequest;
import mcb.com.domain.dto.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(ACCOUNT_PATH)
@RequiredArgsConstructor
public class AppController {
    private final AppService appService;
    @GetMapping(LIST_EVENT_SOURCE_PATH)
    public ResponseEntity<ApiResponse<List<EventSourceResponse>>> listEvents(@RequestParam(value=PAGE, defaultValue = DEFAULT_PAGE) int page,
                                                                      @RequestParam(value=SIZE, defaultValue = DEFAULT_SIZE) int size){
        return appService.listEvents(page, size);
    }
    @GetMapping(LIST_USERS_PATH)
    public ResponseEntity<ApiResponse<List<UsersResponse>>> listUsers(@RequestParam(value=PAGE, defaultValue = DEFAULT_PAGE) int page,
                                                               @RequestParam(value=SIZE, defaultValue = DEFAULT_SIZE) int size){
        return appService.listUsers(page, size);
    }
    @GetMapping(RETRIEVE_SIGNATURE_PATH)
    public ResponseEntity<ApiResponse<EventSourceResponse>> retrieveSignatureInPdf(@RequestParam(EVENT_PID) UUID eventPid){
        return appService.retrieveSignatureInPdf(eventPid);
    }
    @PostMapping(VALIDATE_SIGNATURE_PATH)
    public ResponseEntity<ApiResponse<SignatureValidationResponse>> validateSignature(@Valid @RequestBody ValidateSignatureRequest payload){
        return appService.validateSignature(payload);
    }
    @GetMapping(CURRENCY_PATH)
    public ResponseEntity<ApiResponse<Set<String>>> listCurrency(){
        return appService.listCurrency();
    }
    @GetMapping(LIST_EVENT_SOURCE_SUMMARY_PATH)
   public ResponseEntity<ApiResponse<List<EventSourceSummaryResponse>>> listEventSummary(){
        return appService.listEventSummary();
    }
    @PostMapping(UPDATE_EVENT_SOURCE_PATH)
 public   ResponseEntity<ApiResponse<String>> updateEventSource(@PathVariable(EVENT_PID) UUID eventSourcePid,
                                                          @Valid @RequestBody EventSourceUpdateRequest payload){
        return appService.updateEventSource(eventSourcePid, payload);
    }

}
