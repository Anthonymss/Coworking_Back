package com.coworking.auth_service.service;

import com.coworking.auth_service.dto.UserDto;
import com.coworking.auth_service.service.feignclient.MailServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MailServiceFeignClient mailServiceFeignClient;

    @Async
    public void sendWelcomeEmailAsync(String templateName, UserDto userDtoSend) {
        try {
            mailServiceFeignClient.sendEmailForWelcome("notifications-service",templateName, userDtoSend);
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}