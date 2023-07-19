package mcb.com.api.utils;

import mcb.com.common.SigUtils;
import mcb.com.common.SignatureValidationInternal;
import mcb.com.domain.dto.response.EventSourceResponse;
import mcb.com.domain.dto.response.EventSourceSummaryResponse;
import mcb.com.domain.entity.Users;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TestData {
    public static final String EVENT_SOURCE_PID="0f9dc6bf-19e5-4544-a904-2a28e99e0b54";
    public static final String USER_PID="8f016e8a-ffbe-4217-af9a-880ebad7d169";
    public static final String EVENT_DEPOSIT_ACCOUNT="382105698742";
    public static final String SIGN_PDF= SigUtils.generateBase64PdfSignature(EVENT_DEPOSIT_ACCOUNT);

    public static SignatureValidationInternal getValidatedSignature(){
        return       SigUtils.compareSignatures(EVENT_DEPOSIT_ACCOUNT, EVENT_DEPOSIT_ACCOUNT);
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
    public static List<EventSourceSummaryResponse> listEventSummary(){
        return List.of(EventSourceSummaryResponse.builder()
                        .frequency(14)
                        .label("Not yet verified")
                .build());

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
