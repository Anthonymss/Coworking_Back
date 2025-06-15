package com.coworking.auth_service.service;

import com.coworking.auth_service.dto.AuthRequest;
import com.coworking.auth_service.dto.AuthResponseDto;
import com.coworking.auth_service.dto.UserDto;

import java.util.Map;


public interface UserService {
    String registerUser(UserDto userDto);
    public AuthResponseDto authenticateUser(AuthRequest authRequest);
    public AuthResponseDto generateTokenRegisterForGoogle(String idTokenString);
    public AuthResponseDto generateTokenLoginForGoogle(String tokenGoogle);
}
