package com.carrental.BookingService.Repository;

import com.carrental.BookingService.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking , Integer> {
    List<Booking> findByUserId(Integer userID);
}
