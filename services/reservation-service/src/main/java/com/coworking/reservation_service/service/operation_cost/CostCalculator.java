package com.coworking.reservation_service.service.operation_cost;

import com.coworking.reservation_service.entity.Reservation;
import java.math.BigDecimal;

public interface CostCalculator {
    BigDecimal calculate(BigDecimal pricePerHour, Reservation reservation);
}

