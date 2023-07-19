package mcb.com.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import io.jsonwebtoken.SignatureException;
import mcb.com.api.config.BaseIT;
import mcb.com.api.utils.ApiPath;
import mcb.com.api.utils.TestData;
import mcb.com.api.utils.TestUtil;
import mcb.com.domain.dto.request.StatusEnum;
import mcb.com.domain.dto.request.ValidateSignatureRequest;
import mcb.com.domain.dto.response.*;
import mcb.com.domain.entity.EventSource;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static mcb.com.api.utils.MessageUtil.*;
import static mcb.com.api.utils.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppServiceTest extends BaseIT {
    @Test
    void test_that_listEvent_return_data() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ApiPath.ACCOUNT_PATH + ApiPath.LIST_EVENT_SOURCE_PATH)
                .header(TOKEN_HEADER, ADMIN_TOKEN)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<EventSourceResponse>> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(result.getData().size(), 14);

        assertEquals(result.getMeta().get("totalElement"), 14);
        assertEquals(result.getMeta().get("totalPage"), 1);

    }

    @Test
    void test_that_listUsers_return_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ApiPath.ACCOUNT_PATH + ApiPath.LIST_USERS_PATH)
                .header(TOKEN_HEADER, ADMIN_TOKEN)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<UsersResponse>> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(result.getData().size(), 4);

        assertEquals(result.getMeta().get("totalElement"), 4);
        assertEquals(result.getMeta().get("totalPage"), 1);
    }

    @Test
    void test_that_user_role_not_permit_to_view_user_list() throws Exception {
        mockMvc.perform(get(ApiPath.ACCOUNT_PATH + ApiPath.LIST_USERS_PATH)
                .header(TOKEN_HEADER, USER_TOKEN)
        ).andExpect(status().isForbidden());
    }

    @Test
    void test_for_unauthorized() {
        String errorResponse = "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.";
        SignatureException thrown = assertThrows(SignatureException.class, () -> {
            mockMvc.perform(get(ApiPath.ACCOUNT_PATH + ApiPath.LIST_USERS_PATH)
                    .header(TOKEN_HEADER, BAD_TOKEN)
            ).andExpect(status().isUnauthorized());
        }, errorResponse);
        assertEquals(thrown.getMessage(), errorResponse);

    }

    @Test
    void test_that_retrieveSignatureInPdf_return_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ApiPath.ACCOUNT_PATH+ApiPath.RETRIEVE_SIGNATURE_PATH+"?event-pid="+EVENT_SOURCE_PID)
                .header(TOKEN_HEADER, ADMIN_TOKEN)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<EventSourceResponse> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        EventSourceResponse data = result.getData();
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(data.getPid().toString(), EVENT_SOURCE_PID);
        assertTrue(TestUtil.isValidBase64PDF(data.getSignBase64Pdf()));
    }
    @Test
    void test_that_retrieveSignatureInPdf_return_not_found() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ApiPath.ACCOUNT_PATH+ApiPath.RETRIEVE_SIGNATURE_PATH+"?event-pid="+BAD_EVENT_SOURCE_PID)
                .header(TOKEN_HEADER, ADMIN_TOKEN)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.NOT_FOUND.value());
        assertEquals(result.getMessage(), FAIL);
        assertEquals(result.getData(), RECORD_NOT_FOUND);
    }
    @Test
    void test_that_listCurrency_return_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ApiPath.ACCOUNT_PATH+ApiPath.CURRENCY_PATH)
                .header(TOKEN_HEADER, ADMIN_TOKEN)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<Set<String>> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(result.getData().size(), 7);
        assertEquals(result.getMessage(), SUCCESS);

    }

    @Test
    void test_that_listEventSummary_return_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ApiPath.ACCOUNT_PATH+ApiPath.LIST_EVENT_SOURCE_SUMMARY_PATH)
                .header(TOKEN_HEADER, ADMIN_TOKEN)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<EventSourceSummaryResponse>> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertNotNull(result.getData());
        List<EventSourceSummaryResponse> list = result.getData();
        EventSourceSummaryResponse event = list.get(0);
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(list.size(), 2);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(event.getFrequency(),13);
        assertEquals(event.getLabel(),"Not yet verified");
    }


    @Test
    void test_that_validate_signature_return_not_found() throws Exception {
        ValidateSignatureRequest request =TestData.validateSignatureRequest;
        request.setEventSourcePid(UUID.randomUUID());
        MvcResult mvcResult = mockMvc.perform(post(ApiPath.ACCOUNT_PATH+ApiPath.VALIDATE_SIGNATURE_PATH)
                        .header(TOKEN_HEADER, ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.NOT_FOUND.value());
        assertEquals(result.getMessage(), FAIL);
        assertNotNull(result.getData());
        assertEquals(result.getData(),RECORD_NOT_FOUND);
    }
    @Test
    void test_that_validate_signature_return_unauthorized_access_for_non_admin_user() throws Exception {
       mockMvc.perform(post(ApiPath.ACCOUNT_PATH+ApiPath.VALIDATE_SIGNATURE_PATH)
                        .header(TOKEN_HEADER, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(TestData.validateSignatureRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void test_that_updateEventSource_return_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(ApiPath.ACCOUNT_PATH+ApiPath.LIST_EVENT_SOURCE_PATH+"/update/"+EVENT_SOURCE_PID)
                        .header(TOKEN_HEADER, ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(TestData.eventSourceUpdateRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(result.getMessage(), SUCCESS);
        assertNotNull(result.getData());
        assertEquals(result.getData(),SIGNATURE_VALIDATION_SUCCESSFUL);
        Optional<EventSource> eventSourceOptional= eventSourceRepo.findByPid(UUID.fromString(EVENT_SOURCE_PID));
        assertTrue(eventSourceOptional.isPresent());
        EventSource eventSource = eventSourceOptional.get();
        assertNotNull(eventSource.getUpdatedOn());
        assertEquals("Yes", eventSource.getVerified());
        assertEquals(eventSource.getUpdatedBy().toString(), ADMIN_PID);
        assertEquals(eventSource.getAmountInMur(),1000);
        assertEquals(eventSource.getDebitAccountNumber(), EVENT_DEPOSIT_ACCOUNT);
        assertEquals(eventSource.getDebitAccountCcy(),"GBP");
        assertEquals(eventSource.getDiscrepancyReason(),"Signature Missing");
        assertEquals(eventSource.getComments(),"This is just a test");
        assertEquals(eventSource.getStatus(), StatusEnum.Proceed.name());
    }
    @Test
    void test_that_updateEventSource_return_not_found() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(ApiPath.ACCOUNT_PATH+ApiPath.LIST_EVENT_SOURCE_PATH+"/update/"+UUID.randomUUID())
                        .header(TOKEN_HEADER, ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(TestData.eventSourceUpdateRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.NOT_FOUND.value());
        assertEquals(result.getMessage(), FAIL);
        assertNotNull(result.getData());
        assertEquals(result.getData(),RECORD_NOT_FOUND);
    }

    @Test
    void test_that_updateEventSource_return_unauthorized_access_for_non_admin_user() throws Exception {
        mockMvc.perform(post(ApiPath.ACCOUNT_PATH+ApiPath.LIST_EVENT_SOURCE_PATH+"/update/"+EVENT_SOURCE_PID)
                        .header(TOKEN_HEADER, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(TestData.eventSourceUpdateRequest)))
                .andExpect(status().isForbidden());
    }
    @Test
    void test_that_validate_signature_return_success() throws Exception {
        EventSource event = eventSourceToSave;
        event.setUpdatedBy(UUID.fromString(ADMIN_PID));
        event.setCreatedBy(UUID.fromString(ADMIN_PID));
        event.setPid(TestData.validateSignatureRequest.getEventSourcePid());
        eventSourceRepo.save(event);
        MvcResult mvcResult = mockMvc.perform(post(ApiPath.ACCOUNT_PATH+ApiPath.VALIDATE_SIGNATURE_PATH)
                        .header(TOKEN_HEADER, ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(TestData.validateSignatureRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<SignatureValidationResponse> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(result.getMessage(), SUCCESS);
        assertNotNull(result.getData());
        assertEquals(result.getData().getStatus(),SIGNATURE_VALIDATION_SUCCESSFUL);
    }
}
