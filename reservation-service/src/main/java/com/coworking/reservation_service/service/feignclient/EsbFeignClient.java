package com.coworking.reservation_service.service.feignclient;

import com.coworking.reservation_service.configuration.feigh.FeignClientConfig;
import com.coworking.reservation_service.dto.InvoiceRequest;
import com.coworking.reservation_service.dto.InvoiceResponse;
import com.coworking.reservation_service.dto.ReservationInvoiceDetailsResponse;
import com.coworking.reservation_service.dto.SpaceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "esb-service",configuration = FeignClientConfig.class)
public interface EsbFeignClient {
    @GetMapping("route/api/v1/invoices/create")
    ResponseEntity<InvoiceResponse> createInvoice(
            @RequestBody InvoiceRequest invoiceRequest,
            @RequestHeader("X-Service-Name") String serviceName
    );

    @PostMapping("route/api/v1/notifications/send/reservation")
    ResponseEntity<String> sendEmailForWelcome(
            @RequestParam String templateName,
            @RequestBody ReservationInvoiceDetailsResponse reservationInvoiceDetailsResponse,
            @RequestHeader("X-Service-Name") String serviceName
    );

    @GetMapping("route/api/v1/spaces/price/{id}")
    ResponseEntity<SpaceResponseDto> getInfoSpace(
            @PathVariable Long id,
            @RequestHeader("X-Service-Name") String serviceName
    );
}
