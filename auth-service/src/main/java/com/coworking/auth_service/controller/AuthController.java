package com.coworking.auth_service.controller;

import com.coworking.auth_service.dto.AuthRequest;
import com.coworking.auth_service.dto.AuthResponseDto;
import com.coworking.auth_service.dto.UserDto;
import com.coworking.auth_service.service.IMethodInfoGoogle;
import com.coworking.auth_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private  final UserService userService;
    private final IMethodInfoGoogle infoAccountGoogle;
    @PostMapping("register")
    public ResponseEntity<String> Register(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequest authRequest) {
        AuthResponseDto tokens = userService.authenticateUser(authRequest);
        return ResponseEntity.ok(tokens);
    }
    @PostMapping("/register/oauth2/google")
    public ResponseEntity<AuthResponseDto> googleRegister(@RequestBody Map<String, String> body) {
        AuthResponseDto tokens = userService.generateTokenRegisterForGoogle(body.get("token"));
        return new ResponseEntity<>(tokens, HttpStatus.CREATED);
    }
    @PostMapping("/login/oauth2/google")
    public ResponseEntity<AuthResponseDto> googleLogin(@RequestBody Map<String, String> body) {
        AuthResponseDto tokens = userService.generateTokenLoginForGoogle(body.get("token"));
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/account/google")
    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String,String>> getGoogleAccountInfo(@RequestBody Map<String, String> body) {
        Map<String,String> info = infoAccountGoogle.getInfoForAccountGoogle(body.get("token"));
        return new ResponseEntity<>(info, HttpStatus.OK);
    }


}
