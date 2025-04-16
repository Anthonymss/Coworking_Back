package com.coworking.notifications_service.presentation.controller;

import com.coworking.notifications_service.presentation.dto.ReservationInvoiceDetailsResponse;
import com.coworking.notifications_service.presentation.dto.UserDto;
import com.coworking.notifications_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestParam String templateName, @RequestBody UserDto userDto){
        notificationService.sendNotification(userDto, templateName);
        return new ResponseEntity<>("Notification request received", HttpStatus.OK);
    }
    @PostMapping("/send/reservation")
    public ResponseEntity<String> sendNotificationReservation(
            @RequestParam String templateName, @RequestBody ReservationInvoiceDetailsResponse reservationInvoiceDetailsResponse){
        notificationService.sendNotification(reservationInvoiceDetailsResponse, templateName);
        return new ResponseEntity<>("Notification request received", HttpStatus.OK);
    }
    @GetMapping
    public String getNotificationTemplates() {
        return "hello";
    }
}