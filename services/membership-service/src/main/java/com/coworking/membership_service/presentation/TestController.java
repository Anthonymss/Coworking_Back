package com.coworking.membership_service.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test")
public class TestController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String hello(){return "Hello World by Anthonymss for ROLE_USER!";}
    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public String hello1(){return "Hello World by Anthonymss for ROLE_ADMIN!";}
}
