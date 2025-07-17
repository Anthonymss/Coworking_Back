package com.coworking.reservation_service.service;

import com.coworking.reservation_service.dto.ReservationInvoiceDetailsResponse;
import com.coworking.reservation_service.service.feignclient.EsbFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EsbFeignClient esbFeignClient;

    @Async
    public void sendReservationEmailAsync(String templateName, ReservationInvoiceDetailsResponse reservationInvoiceDetailsResponse) {
        try {
            System.out.println("CUERPO A ENVIAR: "+reservationInvoiceDetailsResponse);
            System.out.println("Tempalte: "+templateName);
            esbFeignClient.sendNotificationReservation(templateName, reservationInvoiceDetailsResponse,"notifications-service");
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
