package com.coworking.notifications_service.service;


import com.coworking.notifications_service.exception.EmailSendFailed;
import com.coworking.notifications_service.exception.TempladeNotFound;
import com.coworking.notifications_service.persistense.entity.NotificationTemplate;
import com.coworking.notifications_service.persistense.entity.UserNotification;
import com.coworking.notifications_service.persistense.repository.NotificationTemplateRepository;
import com.coworking.notifications_service.persistense.repository.UserNotificationRepository;
import com.coworking.notifications_service.presentation.dto.ReservationInvoiceDetailsResponse;
import com.coworking.notifications_service.presentation.dto.UserDto;
import com.coworking.notifications_service.service.mailSend.EmailSenderService;
import com.coworking.notifications_service.util.EmailContentBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {
    private NotificationTemplateRepository templateRepository;
    private UserNotificationRepository notificationRepository;
    private EmailSenderService emailSenderService;
    private EmailContentBuilder emailContentBuilder;

    @Transactional
    public void sendNotification(UserDto userDto, String templateName) {
        Optional<NotificationTemplate> templateOpt = templateRepository.findByName(templateName);
        String email = userDto.getEmail();
        if (templateOpt.isEmpty())
            throw new TempladeNotFound(templateName);
        NotificationTemplate template = templateOpt.get();
        Map<String, String> placeholders = buildMapHolders(userDto);
        String content = emailContentBuilder.build(template.getContent(), placeholders);
        String subject = template.getSubject();
        try {
            emailSenderService.sendEmail(email, subject, content);
        } catch (Exception e) {
            throw new EmailSendFailed(email);
        }
        UserNotification notification = new UserNotification();
        notification.setTemplateId(template.getId());
        notification.setStatus("sent");
        notification.setUserId(userDto.getId());
        notificationRepository.save(notification);
    }

    private Map<String, String> buildMapHolders(UserDto userDto) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("User Name", userDto.getFirstName()+ " "+userDto.getLastName());
        placeholders.put("User Email", userDto.getEmail());
        if(userDto.getAccountCreated() != null)
            placeholders.put("Registration Date",userDto.getAccountCreated().toString());
        else
            placeholders.put("Registration Date", "No se definio");
        return placeholders;
    }
    private Map<String, String> buildMapHolders(ReservationInvoiceDetailsResponse response) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String[] arrayInfoSite=response.getSpaceDetails().split(";");
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("NumeroFactura", response.getInvoiceNumber());
        placeholders.put("FechaReserva",response.getReservationDate().toString());
        placeholders.put("Sitio",arrayInfoSite[0]);
        placeholders.put("Direccion",arrayInfoSite[1]);
        placeholders.put("NombreSpacio",arrayInfoSite[2]);
        placeholders.put("Duracion",response.getDurationRange());
        placeholders.put("MetodoPago",response.getPaymentMethod());
        placeholders.put("Subtotal", df.format(response.getSubtotal()));
        placeholders.put("Impuesto", df.format(response.getTaxAmount()));
        placeholders.put("Total", df.format(response.getTotalCost()));
        return placeholders;
    }

    public void sendNotification(ReservationInvoiceDetailsResponse reservationInvoiceDetailsResponse, String templateName) {
        Optional<NotificationTemplate> templateOpt = templateRepository.findByName(templateName);
        String email = reservationInvoiceDetailsResponse.getEmail();
        if (templateOpt.isEmpty())
            throw new TempladeNotFound(templateName);
        NotificationTemplate template = templateOpt.get();
        Map<String, String> placeholders = buildMapHolders(reservationInvoiceDetailsResponse);
        String content = emailContentBuilder.build(template.getContent(), placeholders);
        String subject = template.getSubject();
        try {
            emailSenderService.sendEmail(email, subject, content);
        } catch (Exception e) {
            throw new EmailSendFailed(email);
        }
        UserNotification notification = new UserNotification();
        notification.setTemplateId(template.getId());
        notification.setStatus("sent");
        notification.setUserId(reservationInvoiceDetailsResponse.getUser_id());
        notificationRepository.save(notification);
    }
}
