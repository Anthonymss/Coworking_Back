package com.coworking.reservation_service.service.feignclient;

import com.coworking.reservation_service.presentation.dto.ReservationInvoiceDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notifications-service")
public interface MailServiceFeignClient {
    @PostMapping("/api/v1/notifications/send/reservation")
    ResponseEntity<String> sendEmailForWelcome(@RequestParam String templateName, @RequestBody ReservationInvoiceDetailsResponse reservationInvoiceDetailsResponse);
}