package com.mercado.service;

import com.mercado.domain.PaymentMembership;
import com.mercado.domain.PaymentMercado;
import com.mercado.repository.PaymentMembershipRepository;
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
    @Autowired
    private PaymentMembershipRepository paymentMembershipRepository;

    public void registrarPago(Payment payment) {
        Map<String, Object> metadata = payment.getMetadata();
        System.out.println("[DEBUG] Metadata recibida: " + metadata);
        if (metadata == null || ( !metadata.containsKey("reserva_id") && !metadata.containsKey("membresia_id"))) {
            return;
        }
        String metodo = convertirMetodo(payment.getPaymentMethodId());
        if(metadata.containsKey("membresia_id")) {
            Double membresiaIdDouble = Double.valueOf(metadata.get("membresia_id").toString());
            Long membresiaId = membresiaIdDouble.longValue();
            System.out.println("[registrarPago] Membresía ID obtenida: " + membresiaId);
            PaymentMembership pagoMembresia = new PaymentMembership();
            pagoMembresia.setMembershipId(membresiaId);
            pagoMembresia.setAmount(payment.getTransactionAmount().doubleValue());
            pagoMembresia.setMethod(metodo);
            pagoMembresia.setStatus(payment.getStatus());
            pagoMembresia.setPaymentDate(LocalDateTime.now());
            pagoMembresia.setPaymentId(payment.getId().toString());
            if (payment.getPayer() != null){pagoMembresia.setPayerEmail(payment.getPayer().getEmail());}
            paymentMembershipRepository.save(pagoMembresia);
            System.out.println("[registrarPagoMembresia] Pago guardado en la base de datos.");
        }if (metadata.containsKey("reserva_id")) {
            Double reservaIdDouble = Double.valueOf(metadata.get("reserva_id").toString());
            Long reservaId = reservaIdDouble.longValue();
            System.out.println("[registrarPago] Reserva ID obtenida: " + reservaId);
            PaymentMercado nuevoPago = new PaymentMercado();
            nuevoPago.setReservationId(reservaId);
            nuevoPago.setAmount(payment.getTransactionAmount().doubleValue());
            nuevoPago.setMethod(metodo);
            nuevoPago.setStatus(payment.getStatus());
            nuevoPago.setPaymentDate(LocalDateTime.now());
            nuevoPago.setPaymentId(payment.getId().toString());
            if (payment.getPayer() != null) {nuevoPago.setPayerEmail(payment.getPayer().getEmail());}
            paymentRepository.save(nuevoPago);
            System.out.println("RegistarPagoEspacio , Pago guardado en la base de datos");
        }
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
