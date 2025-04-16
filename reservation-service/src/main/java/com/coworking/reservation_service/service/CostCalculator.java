package com.coworking.reservation_service.service;

import com.coworking.reservation_service.persistence.entity.Reservation;
import java.math.BigDecimal;

public interface CostCalculator {
    BigDecimal calculate(BigDecimal pricePerHour, Reservation reservation);
}

