package mcb.com.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import mcb.com.api.service.AppService;
import mcb.com.api.utils.ApiPath;
import mcb.com.api.utils.TestData;
import mcb.com.domain.dto.request.ValidateSignatureRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static mcb.com.api.utils.MessageUtil.SIGNATURE_VALIDATION_SUCCESSFUL;
import static mcb.com.api.utils.MessageUtil.SUCCESS;
import static mcb.com.api.utils.TestData.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppController.class)
class AppControllerTest extends BaseControllerTest{
    private ObjectMapper objectMapper;
@MockBean
private AppService appService;
    @BeforeEach
    public void setUp() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();

    }

    @Test
    @WithMockUser(roles = "USER")
    void test_that_list_events_return_success() throws Exception {
        when(appService.listEvents(anyInt(), anyInt())).thenReturn(TestData.listEventTestData());
        mockMvc.perform(get(ApiPath.ACCOUNT_PATH+ApiPath.LIST_EVENT_SOURCE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.meta.totalElement").value(1))
                .andExpect(jsonPath("$.meta.totalPage").value(1))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @WithMockUser(roles = "SUPER")
    void test_that_list_user_return_success() throws Exception {
        when(appService.listUsers(anyInt(), anyInt())).thenReturn(TestData.listUserTestData());
        mockMvc.perform(get(ApiPath.ACCOUNT_PATH+ApiPath.LIST_USERS_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.meta.totalElement").value(2))
                .andExpect(jsonPath("$.meta.totalPage").value(1))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @WithMockUser(roles = "SUPER")
    void test_that_retrieve_signature_pdf_return_success() throws Exception {
        when(appService.retrieveSignatureInPdf(any())).thenReturn(TestData.retrieveSignatureInPdfTestData());
        mockMvc.perform(get(ApiPath.ACCOUNT_PATH+ApiPath.RETRIEVE_SIGNATURE_PATH+"?event-pid="+EVENT_SOURCE_PID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.accountShortName").value("test"))
                .andExpect(jsonPath("$.data.pid").value(EVENT_SOURCE_PID))
                .andExpect(jsonPath("$.data.signBase64Pdf").value(SIGN_PDF));
    }

    @Test
    @WithMockUser( roles = {"ADMINISTRATOR","USER", "SUPER"})
    void test_that_validate_signature_return_success() throws Exception {
        when(appService.validateSignature(any())).thenReturn(TestData.validateSignatureResponseData());
        mockMvc.perform(post(ApiPath.ACCOUNT_PATH+ApiPath.VALIDATE_SIGNATURE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ValidateSignatureRequest.builder()
                                        .eventAccountNumber(EVENT_DEPOSIT_ACCOUNT)
                                        .eventSourcePid(UUID.fromString(EVENT_SOURCE_PID))
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.signImage").value(validatedSign.getSignImage()))
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()));

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR","USER", "SUPER"})
    void listCurrency() throws Exception {
        when(appService.listCurrency()).thenReturn(TestData.listCurrencyResponse());
        mockMvc.perform(get(ApiPath.ACCOUNT_PATH+ApiPath.CURRENCY_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(7)));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    void listEventSummary() throws Exception {
        when(appService.listEventSummary()).thenReturn(TestData.listEventSummaryResponse());
        mockMvc.perform(get(ApiPath.ACCOUNT_PATH+ApiPath.LIST_EVENT_SOURCE_SUMMARY_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    void test_that_update_EventSource_return_success() throws Exception {
        when(appService.updateEventSource(any(),any())).thenReturn(TestData.updateEventData());
        mockMvc.perform(post(ApiPath.ACCOUNT_PATH+ApiPath.LIST_EVENT_SOURCE_PATH+"/update/"+EVENT_SOURCE_PID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.eventSourceUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(SIGNATURE_VALIDATION_SUCCESSFUL))
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()));
    }
}
