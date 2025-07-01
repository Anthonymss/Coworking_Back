package com.mercado.controller;


import com.mercado.MercadoPagoRequest;
import com.mercado.domain.Payment;
import com.mercado.repository.PaymentRepository;
import com.mercado.service.MercadoPagoService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/mercado")
@CrossOrigin(origins = "*")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    public String crearPreferencia(@RequestBody MercadoPagoRequest requestBody) {
        try {
            String initPoint = mercadoPagoService.crearPreferencia(requestBody);
            return "{\"init_point\": \"" + initPoint + "\"}";
        } catch (MPApiException e) {
            System.out.println("Error API Mercado Pago: " + e.getApiResponse().getContent());
            return "{\"error\": \"Fallo API Mercado Pago: " + e.getApiResponse().getContent() + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Fallo inesperado: " + e.getMessage() + "\"}";
        }
    }

    @PostMapping({"/webhook"})
    public ResponseEntity<String> recibirWebhook(@RequestBody Map<String, Object> payload){
        System.out.println("🧠 Webhook recibido: " + payload);
        System.out.println("============================");
        System.out.println("\ud83d\udca5 LLEGÓ AL WEBHOOK");
        System.out.println("===> Webhook recibido (RAW): " + String.valueOf(payload));
        System.out.println("============================");

        try {
        String type = (String) payload.get("type");
        Object actionObj = payload.get("action");
        String action = actionObj != null ? actionObj.toString() : "";

        if ("payment".equals(type) && action.contains("payment.")) {
            Map<String, Object> data = (Map) payload.get("data");
            String paymentId = data.get("id").toString();
            MercadoPagoConfig.setAccessToken("APP_USR-205648492046855-061903-34ff418973343a248f6cf25a3e81d796-2488322797");
            PaymentClient paymentClient = new PaymentClient();
            com.mercadopago.resources.payment.Payment payment = paymentClient.get(Long.parseLong(paymentId));
            System.out.println("🧠 Metadata del pago: " + payment.getMetadata());

            System.out.println("💳 Estado del pago: " + payment.getStatus());

            if ("approved".equals(payment.getStatus())) {
                Map<String, Object> metadata = payment.getMetadata();
                Double reservaIdDouble = Double.valueOf(metadata.get("reserva_id").toString());
                Long reservaId = reservaIdDouble.longValue();

                if (metadata == null || !metadata.containsKey("reserva_id")) {
                    System.out.println("❌ Metadata vacía o no contiene reservaId.");
                    return ResponseEntity.ok("Webhook sin reservaId, se omite.");
                }

                //Double reservaIdDouble = Double.valueOf(metadata.get("reserva_id").toString());
                //Long reservaId = reservaIdDouble.longValue();

                Payment nuevoPago = new Payment();
                nuevoPago.setReservationId(reservaId);
                nuevoPago.setAmount(payment.getTransactionAmount().doubleValue());
                nuevoPago.setMethod("Mercado Pago");
                nuevoPago.setStatus(payment.getStatus());
                nuevoPago.setPaymentDate(LocalDateTime.now());

                paymentRepository.save(nuevoPago);

                System.out.println("✅ Pago registrado con reservaId " + reservaId);
            }
        }

        return ResponseEntity.ok("Webhook procesado correctamente");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Error procesando el webhook");
    }
}
}
