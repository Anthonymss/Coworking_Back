package com.coworking.management_user.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
