package com.mercado.service;
import com.mercado.MercadoPagoRequest;
import com.mercado.config.MercadoPagoProperties;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MercadoPagoService {

    @Autowired
    private PaymentService paymentService;

    private final MercadoPagoProperties config;

    @Autowired
    public MercadoPagoService(MercadoPagoProperties config) {
        this.config = config;
    }

    public String crearPreferencia(MercadoPagoRequest requestBody) throws MPApiException, MPException {
        MercadoPagoConfig.setAccessToken(config.getAccessToken());

        PreferenceClient client = new PreferenceClient();

        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title(requestBody.getTitle())
                .quantity(requestBody.getQuantity())
                .unitPrice(new BigDecimal(requestBody.getUnitPrice()))
                .currencyId("PEN")
                .build();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://www.google.com")
                .pending("https://www.google.com")
                .failure("https://www.google.com")
                .build();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("reserva_id", requestBody.getReservaId());

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(List.of(item))
                .backUrls(backUrls)
                .autoReturn("approved")
                .metadata(metadata)
                .build();

        Preference preference = client.create(preferenceRequest);

        return preference.getInitPoint();
    }

    public String procesarWebhook(Map<String, Object> payload) {
        try {
            String type = (String) payload.get("type");
            Object actionObj = payload.get("action");
            String action = actionObj != null ? actionObj.toString() : "";

            if ("payment".equals(type) && action.contains("payment.")) {
                Map<String, Object> data = (Map) payload.get("data");
                String paymentId = data.get("id").toString();

                MercadoPagoConfig.setAccessToken(config.getAccessToken());
                PaymentClient paymentClient = new PaymentClient();
                Payment payment = paymentClient.get(Long.parseLong(paymentId));

                System.out.println("🧠 Metadata del pago: " + payment.getMetadata());
                System.out.println("💳 Estado del pago: " + payment.getStatus());

                if ("approved".equals(payment.getStatus())) {
                    paymentService.registrarPago(payment);
                }
            }

            return "Webhook procesado correctamente";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error procesando el webhook: " + e.getMessage();
        }
    }


}
