package com.coworking.reservation_service.service;

import com.coworking.reservation_service.presentation.dto.ReservationInvoiceDetailsResponse;
import com.coworking.reservation_service.service.feignclient.MailServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MailServiceFeignClient mailServiceFeignClient;

    @Async
    public void sendReservationEmailAsync(String templateName, ReservationInvoiceDetailsResponse reservationInvoiceDetailsResponse) {
        try {
            mailServiceFeignClient.sendEmailForWelcome(templateName, reservationInvoiceDetailsResponse);
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
