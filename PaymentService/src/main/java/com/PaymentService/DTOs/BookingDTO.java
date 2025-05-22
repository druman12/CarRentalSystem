package com.PaymentService.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTO {
    private String bookingId;
    private String customerId;
    private String carId;
    private String companyId;
    private LocalDate pickupDate;
    private LocalDate returnDate;
    private int totalDays;
    private double totalAmount;
    private String status;
    private String bookingReference;
}

