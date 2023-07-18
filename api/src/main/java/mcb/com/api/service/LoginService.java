package mcb.com.api.service;

import mcb.com.domain.dto.request.Login;
import mcb.com.domain.dto.response.ApiResponse;
import mcb.com.domain.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<ApiResponse<LoginResponse>> login(Login payload);
}
