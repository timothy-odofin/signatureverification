package mcb.com.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import mcb.com.api.service.LoginService;
import mcb.com.api.utils.TestData;
import mcb.com.domain.dto.response.LoginResponse;
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

import static mcb.com.api.utils.MessageUtil.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EntranceController.class)
class EntranceControllerTest extends BaseControllerTest{
    @MockBean
    private LoginService loginService;
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setUp() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        LocalDateDeserializer localDateDeserializer =  new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();

    }
@Test
    void test_that_login_return_success() throws Exception {
when(loginService.login(any())).thenReturn(TestData.loginResponse());
    LoginResponse loginResponse = TestData.loginData();
    mockMvc.perform(post("/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TestData.loginRequest())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.pid").value(loginResponse.getPid()))
            .andExpect(jsonPath("$.message").value(SUCCESS))
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.firstName").value(loginResponse.getFirstName()))
            .andExpect(jsonPath("$.data.lastName").value(loginResponse.getLastName()))
            .andExpect(jsonPath("$.data.businessUnit").value(loginResponse.getBusinessUnit()))
            .andExpect(jsonPath("$.data.token.roles").isArray()) // Check if roles is an array
            .andExpect(jsonPath("$.data.token.roles[0]").value("ROLE_USER"));

}
}
