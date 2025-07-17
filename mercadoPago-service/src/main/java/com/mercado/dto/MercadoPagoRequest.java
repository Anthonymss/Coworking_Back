package com.mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoRequest {
    private String title;
    private int quantity;
    private double unitPrice;
    private Long reservaId;
    private Long membresiaId;

}
