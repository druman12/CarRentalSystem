package com.PaymentService.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Payment {

    @Id
    private Integer bookingId; // Primary key

    @Column(nullable = false)
    private Integer companyId;

    private BigDecimal amount;

    private String paymentMethod = "cash";

    private String status = "pending";

    private LocalDateTime createdAt = LocalDateTime.now();
}