package com.PaymentService.DTOs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequest {
    private UUID bookingId;
    private UUID companyId;
    private BigDecimal amount;
    private String paymentMethod;
    private String gatewayTransactionId;
}
