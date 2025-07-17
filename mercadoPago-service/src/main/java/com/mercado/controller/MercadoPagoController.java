package com.mercado.controller;


import com.mercado.dto.MercadoPagoRequest;
import com.mercado.repository.PaymentRepository;
import com.mercado.service.MercadoPagoService;
import com.mercadopago.exceptions.MPApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestBody Map<String, Object> payload) {
        System.out.println("Webhook recibido: " + payload);
        String resultado = mercadoPagoService.procesarWebhook(payload);
        return ResponseEntity.ok(resultado);
    }
}
