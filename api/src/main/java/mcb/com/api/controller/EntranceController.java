package mcb.com.api.controller;

import mcb.com.domain.dto.request.Login;
import mcb.com.domain.dto.response.ApiResponse;
import mcb.com.domain.dto.response.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static mcb.com.api.utils.MessageUtil.SUCCESS;

@RestController
@RequestMapping("/auth")
public class EntranceController {
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Login payload){
        return ResponseEntity.ok(new ApiResponse(SUCCESS, HttpStatus.OK.value(), new JwtResponse()));

    }
}
