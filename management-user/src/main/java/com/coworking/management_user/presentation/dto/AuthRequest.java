package com.coworking.management_user.presentation.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
