package com.mercado.service;

import com.mercado.domain.PaymentMercado;
import com.mercadopago.resources.payment.Payment;

import com.mercado.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public void registrarPago(Payment payment) {
        System.out.println("[registrarPago] Iniciando registro del pago...");

        Map<String, Object> metadata = payment.getMetadata();
        if (metadata == null || !metadata.containsKey("reserva_id")) {
            System.out.println("[registrarPago] ❌ Metadata no contiene reserva_id");
            return;
        }

        Double reservaIdDouble = Double.valueOf(metadata.get("reserva_id").toString());
        Long reservaId = reservaIdDouble.longValue();
        System.out.println("[registrarPago] Reserva ID obtenida: " + reservaId);

        String metodo = convertirMetodo(payment.getPaymentMethodId());

        PaymentMercado nuevoPago = new PaymentMercado();
        nuevoPago.setReservationId(reservaId);
        nuevoPago.setAmount(payment.getTransactionAmount().doubleValue());
        nuevoPago.setMethod(metodo);
        nuevoPago.setStatus(payment.getStatus());
        nuevoPago.setPaymentDate(LocalDateTime.now());
        nuevoPago.setPaymentId(payment.getId().toString());
        if (payment.getPayer() != null) {
            nuevoPago.setPayerEmail(payment.getPayer().getEmail());
        }


        paymentRepository.save(nuevoPago);
        System.out.println("[registrarPago] ✅ Pago guardado en la base de datos.");
    }

    private String convertirMetodo(String metodoId) {
        return switch (metodoId) {
            case "master" -> "MasterCard";
            case "visa" -> "Visa";
            case "amex" -> "American Express";
            case "paypal" -> "PayPal";
            case "debmaster" -> "Débito MasterCard";
            case "debvisa" -> "Débito Visa";
            default -> metodoId;
        };
    }
}
