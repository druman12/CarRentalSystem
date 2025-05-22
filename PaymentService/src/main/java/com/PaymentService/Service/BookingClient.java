package com.PaymentService.Service;

import com.PaymentService.DTOs.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "BOOKING-SERVICE")
public interface BookingClient {
    @GetMapping("/api/bookings/{id}")
    BookingDTO getBookingById(@PathVariable("id") Integer bookingId);
}
