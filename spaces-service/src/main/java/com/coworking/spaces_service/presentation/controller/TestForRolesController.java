package com.coworking.spaces_service.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestForRolesController {

    @GetMapping("/user")
    public String getUserTest() {
        return "User Test";
    }
    @GetMapping("/admin")
    public String getAdminTest() {
        return "Admin Test";
    }
}
