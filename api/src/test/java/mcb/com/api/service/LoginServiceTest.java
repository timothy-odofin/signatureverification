package mcb.com.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import mcb.com.api.config.BaseIT;
import mcb.com.api.utils.ApiPath;
import mcb.com.api.utils.TestData;
import mcb.com.domain.dto.response.ApiResponse;
import mcb.com.domain.dto.response.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static mcb.com.api.utils.MessageUtil.*;
import static mcb.com.api.utils.TestData.ADMIN_PID;
import static mcb.com.api.utils.TestData.USER_PID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Slf4j
class LoginServiceTest extends BaseIT {

@Test
    void test_that_user_can_login() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post(ApiPath.AUTH_PATH)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(TestData.userLoginRequest())))
            .andExpect(status().isOk()).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    ApiResponse<LoginResponse> result = objectMapper.readValue(content, new TypeReference<>() {
    });
    assertEquals(result.getCode(), HttpStatus.OK.value());
    assertEquals(result.getMessage(), SUCCESS);
    assertNotNull(result.getData());
    assertEquals(result.getData().getPid(), USER_PID);
    assertEquals(result.getData().getToken().getRoles().size(),1);
}

    @Test
    void test_that_admin_can_login() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(ApiPath.AUTH_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(TestData.loginRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<LoginResponse> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(result.getMessage(), SUCCESS);
        assertNotNull(result.getData());
        assertEquals(result.getData().getPid(), ADMIN_PID);
        assertEquals(result.getData().getToken().getRoles().size(),2);

    }

    @Test
    void test_that_login_return_invalid_with_incorrect_username_or_password() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(ApiPath.AUTH_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(TestData.invalidLoginRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(result.getCode(), HttpStatus.NOT_FOUND.value());
        assertEquals(result.getMessage(), FAIL);
        assertNotNull(result.getData());
        assertEquals(result.getData(), USER_NOT_FOUND);

    }
}
