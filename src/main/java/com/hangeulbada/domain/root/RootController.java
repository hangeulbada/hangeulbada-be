package com.hangeulbada.domain.root;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class RootController {
    @GetMapping("/")
    public String root() {
        return "hangeul bada HTTPS API\n 한글바다 HTTPS API 서버입니다.";
    }
}
