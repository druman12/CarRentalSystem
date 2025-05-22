package com.PaymentService.Service;


import com.PaymentService.DTOs.PaymentRequest;
import com.PaymentService.DTOs.PaymentResponse;
import com.PaymentService.Entity.Payment;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);
    Payment getPaymentByBookingId(Integer bookingId);
}
