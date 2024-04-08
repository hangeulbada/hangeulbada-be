package com.hangeulbada.domain.login.controller;

import com.hangeulbada.domain.login.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        loginService.socialLogin(code, registrationId);
    }
}