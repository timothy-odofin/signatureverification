package mcb.com.api.controller;

import lombok.RequiredArgsConstructor;
import mcb.com.api.service.LoginService;
import mcb.com.domain.dto.request.Login;
import mcb.com.domain.dto.response.ApiResponse;
import mcb.com.domain.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static mcb.com.api.utils.ApiPath.AUTH_PATH;

@RestController
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
public class EntranceController {
    private final LoginService loginService;
    @PostMapping
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody Login payload){
        return loginService.login(payload);
    }
}
