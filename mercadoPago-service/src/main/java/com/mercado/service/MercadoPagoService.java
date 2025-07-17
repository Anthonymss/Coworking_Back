package com.mercado.service;
import com.mercado.dto.MercadoPagoRequest;
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
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MercadoPagoService {
    @Autowired
    private RestTemplate restTemplate;

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
        if (requestBody.getReservaId() != null && requestBody.getReservaId() > 0) {
            metadata.put("reserva_id", requestBody.getReservaId());
        }
        if (requestBody.getMembresiaId() != null && requestBody.getMembresiaId() > 0) {
            metadata.put("membresia_id", requestBody.getMembresiaId());
        }
        System.out.println("[DEBUG] Metadata enviada en preferencia: " + metadata);
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

                if ("approved".equals(payment.getStatus())) {
                    Map<String, Object> metadata = payment.getMetadata();
                    if(metadata.containsKey("reserva_id")){
                        //Double reservaIdDouble = Double.valueOf(payment.getMetadata().get("reserva_id").toString());
                        Double reservaIdDouble = Double.valueOf(metadata.get("reserva_id").toString());
                        Long reservaId = reservaIdDouble.longValue();
                        paymentService.registrarPago(payment);
                        String url = "http://localhost:8087/api/v1/invoices/actualizar-estado/" + reservaId + "?nuevoEstado=Paid";
                        try {
                            restTemplate.put(url, null);
                            System.out.println("Estado de la factura actualizado a Paid");
                        } catch (Exception ex) {
                            System.out.println("Error actualizando estado de factura: " + ex.getMessage());
                        }
                    }else if(metadata.containsKey("membresia_id")){
                        Double membresiaIdDouble = Double.valueOf(metadata.get("membresia_id").toString());
                        Long membresiaId = membresiaIdDouble.longValue();
                        paymentService.registrarPago(payment);
                    }
                }
            }
            return "Webhook procesado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error procesando el webhook: " + e.getMessage();
        }
    }
}
