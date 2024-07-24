package com.hangeulbada.domain.root;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class RootController {
    @GetMapping("/")
    public String root() {
        return "hangeulbada API server";
    }
}
