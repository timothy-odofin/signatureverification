package mcb.com.api.utils;

import mcb.com.api.mapper.Mapper;
import mcb.com.common.SigUtils;
import mcb.com.common.SignatureValidationInternal;
import mcb.com.domain.dto.request.EventSourceUpdateRequest;
import mcb.com.domain.dto.request.Login;
import mcb.com.domain.dto.request.StatusEnum;
import mcb.com.domain.dto.response.*;
import mcb.com.domain.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static mcb.com.api.utils.MessageUtil.*;

public class TestData {
    public static final String EVENT_SOURCE_PID="0f9dc6bf-19e5-4544-a904-2a28e99e0b54";
    public static final String USER_PID="8f016e8a-ffbe-4217-af9a-880ebad7d169";
    public static final String EVENT_DEPOSIT_ACCOUNT="382105698742";
    public static final String SIGN_PDF= SigUtils.generateBase64PdfSignature(EVENT_DEPOSIT_ACCOUNT);
    public static final SignatureValidationInternal validatedSign= SigUtils.compareSignatures(EVENT_DEPOSIT_ACCOUNT, EVENT_DEPOSIT_ACCOUNT);
    public static final EventSourceUpdateRequest eventSourceUpdateRequest=EventSourceUpdateRequest.builder()
            .actionStatus(StatusEnum.Proceed)
            .transactionAmount(3000)
            .amountInMur(1000)
            .transactionCurrency("USD")
            .debitAccountNumber(EVENT_DEPOSIT_ACCOUNT)
            .debitAccountCcy("GBP")
            .verified("Yes")
            .discrepancyReason("Test")
            .comments("This is just a test")
            .build();

  public static  ResponseEntity<ApiResponse<List<EventSourceSummaryResponse>>> listEventSummaryResponse(){
      return ResponseEntity.ok(new ApiResponse<>(SUCCESS,HttpStatus.OK.value(), listEventSummary()));

    }

 public static ResponseEntity<ApiResponse<String>> updateEventData(){
     return ResponseEntity.ok(new ApiResponse<>(SUCCESS, HttpStatus.OK.value(), SIGNATURE_VALIDATION_SUCCESSFUL));

 }
    public static ResponseEntity<ApiResponse<SignatureValidationResponse>> validateSignatureResponseData(){
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS,HttpStatus.OK.value(),
                SignatureValidationResponse.builder()
                        .signImage(validatedSign.getSignImage())
                        .status(validatedSign.isValid()? SIGNATURE_VALIDATION_SUCCESSFUL:SIGNATURE_VALIDATION_FAIL)
                        .build()));

    }
   public static ResponseEntity<ApiResponse<List<EventSourceResponse>>> listEventTestData(){

       return ResponseEntity.ok(new ApiResponse<>(MessageUtil.SUCCESS, HttpStatus.OK.value(),
               List.of(EventSourceResponse.builder()
                               .accountShortName("test")
                               .pid(UUID.fromString(EVENT_SOURCE_PID))
                       .build()))
               .addMeta("totalElement", 1)
               .addMeta("totalPage", 1));
   }
    public static ResponseEntity<ApiResponse<EventSourceResponse>> retrieveSignatureInPdfTestData(){

        return ResponseEntity.ok(new ApiResponse<>(MessageUtil.SUCCESS, HttpStatus.OK.value(),
               EventSourceResponse.builder()
                        .accountShortName("test")
                        .pid(UUID.fromString(EVENT_SOURCE_PID))
                       .signBase64Pdf(SIGN_PDF)
                        .build()));
    }
    public static Set<String> listCurrency(){
        return new LinkedHashSet<>(List.of( "ARS",
                "CNY",
                "EUR",
                "IDR",
                "PHP",
                "VND",
                "XOF"));
    }
    public static ResponseEntity<ApiResponse<Set<String>>> listCurrencyResponse(){
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS,HttpStatus.OK.value(),
                listCurrency()));


    }
    public static List<EventSourceSummaryResponse> listEventSummary(){
        return List.of(EventSourceSummaryResponse.builder()
                        .frequency(14)
                        .label("Not yet verified")
                .build());

    }
    public static Login loginRequest(){

        return Login.builder()
                .password("test")
                .username("john_smith123")
                .build();

    }
  public static  ResponseEntity<ApiResponse<List<UsersResponse>>> listUserTestData(){
      return ResponseEntity.ok(new ApiResponse<>(MessageUtil.SUCCESS, HttpStatus.OK.value(),
              Mapper.convertList(List.of(expectedNormalUser(), expectedAdminUser()), UsersResponse.class))
              .addMeta("totalElement", 2)
              .addMeta("totalPage", 1));

  }
    public static Users expectedAdminUser(){
        Users user = Users.builder()
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@example.com")
                .username("john_smith123")
                .businessUnit("BU1")
                .active(true)
                .accountStatus("HOD")
                .build();
        user.setPid(UUID.fromString("8f016e8a-ffbe-4217-af9a-880ebad7d169"));
        return user;
    }
public static LoginResponse loginData(){
        return LoginResponse.builder()
                .businessUnit("BU2")
                .pid("a01f92d3-cc13-4d69-b0de-2537e0e6070a")
                .firstName("Emily")
                .lastName("Johnson")
                .token(AuthTokenInfo.builder()
                        .roles(List.of("ROLE_USER"))
                        .build())
                .build();

}
public static ResponseEntity<ApiResponse<LoginResponse>> loginResponse(){
    return ResponseEntity.ok(new ApiResponse<>(SUCCESS, HttpStatus.OK.value(), loginData()));

}
    public static Users expectedNormalUser(){
        Users user = Users.builder()
                .firstName("Emily")
                .lastName("Johnson")
                .email("emily.johnson@example.com")
                .username("emily_89")
                .businessUnit("BU2")
                .active(true)
                .accountStatus("MARKETING")
                .build();
        user.setPid(UUID.fromString("a01f92d3-cc13-4d69-b0de-2537e0e6070a"));
        return user;
    }

}
