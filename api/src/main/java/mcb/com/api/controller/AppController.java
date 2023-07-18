package mcb.com.api.controller;

import static mcb.com.api.utils.ApiPath.*;

import lombok.RequiredArgsConstructor;
import mcb.com.api.service.AppService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ACCOUNT_PATH)
@RequiredArgsConstructor
public class AppController {
    private final AppService appService;
}
