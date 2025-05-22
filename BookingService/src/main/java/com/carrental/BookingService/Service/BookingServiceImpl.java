package com.carrental.BookingService.Service;

import com.carrental.BookingService.DTO.BookingDetailsResponse;
import com.carrental.BookingService.DTO.Car;
import com.carrental.BookingService.DTO.RentalCompany;
import com.carrental.BookingService.Entity.Booking;
import com.carrental.BookingService.Feign.RentalCompanyClient;
import com.carrental.BookingService.Feign.CarClient;
import com.carrental.BookingService.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private  BookingRepository bookingRepository;
    @Autowired
    private ResilientService resilientService;


    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(int id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking addBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(int id, Booking bookingDetails) {
        return bookingRepository.findById(id).map(booking -> {
            if (bookingDetails.getCompanyID() != 0) booking.setCompanyID(bookingDetails.getCompanyID());
            if (bookingDetails.getUserId() != 0) booking.setUserId(bookingDetails.getUserId());
            if (bookingDetails.getCarId() != 0) booking.setCarId(bookingDetails.getCarId());
            if (bookingDetails.getPickupDate() != null) booking.setPickupDate(bookingDetails.getPickupDate());
            if (bookingDetails.getReturnDate() != null) booking.setReturnDate(bookingDetails.getReturnDate());
            if (bookingDetails.getTotalDays() != 0) booking.setTotalDays(bookingDetails.getTotalDays());
            if (bookingDetails.getTotalAmount() != 0) booking.setTotalAmount(bookingDetails.getTotalAmount());
            if (bookingDetails.getStatus() != null && !bookingDetails.getStatus().isEmpty())
                booking.setStatus(bookingDetails.getStatus());
            if (bookingDetails.getBookingReference() != null && !bookingDetails.getBookingReference().isEmpty())
                booking.setBookingReference(bookingDetails.getBookingReference());

            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
    }

    @Override
    public void deleteBooking(int id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getBookingByUserId(int userID) {
        return bookingRepository.findByUserId(userID);
    }

    @Override
    public BookingDetailsResponse getBookingDetails(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Car car = resilientService.getCarById(booking.getCarId());
        RentalCompany company = (car != null) ? resilientService.getCompanyById(car.getCompanyId()) : null;

        BookingDetailsResponse response = new BookingDetailsResponse();
        response.setBooking(booking);
        response.setCar(car);
        response.setRentalCompany(company);

        return response;
    }


}
