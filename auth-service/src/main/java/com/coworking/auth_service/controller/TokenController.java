package com.coworking.auth_service.controller;

import com.coworking.auth_service.configuration.jwt.JwtTokenProvider;
import com.coworking.auth_service.entity.User;
import com.coworking.auth_service.exception.InvalidJwtTokenException;
import com.coworking.auth_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class TokenController {
    private final JwtTokenProvider jwtProvider;
    private final UserRepository userRepo;

    @PostMapping("/token/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> body) {

        System.out.println("BODY : "+body);
        String refreshToken = body.get("refreshToken");
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new InvalidJwtTokenException("Refresh token inválido");
        }
        String email = jwtProvider.getRefreshClaims(refreshToken).getSubject();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        String newAccessToken = jwtProvider.generateAccessToken(user);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
