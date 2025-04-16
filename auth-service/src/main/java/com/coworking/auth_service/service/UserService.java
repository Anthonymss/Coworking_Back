package com.coworking.auth_service.service;

import com.coworking.auth_service.presentation.dto.AuthRequest;
import com.coworking.auth_service.presentation.dto.UserDto;

import java.util.Map;


public interface UserService {
    String registerUser(UserDto userDto);
    Map<String,String> authenticateUser(AuthRequest authRequest);
    public Map<String,String> generateTokenRegisterForGoogle(String tokenGoogle);
    public Map<String,String> generateTokenLoginForGoogle(String tokenGoogle);
}
