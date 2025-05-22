package com.PaymentService.Service;


import com.PaymentService.DTOs.PaymentRequest;
import com.PaymentService.DTOs.PaymentResponse;
import com.PaymentService.Entity.Payment;
import com.PaymentService.Repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepo;
    private final BookingClient bookingClient;
    private final RentalCompanyClient companyClient;

    public PaymentServiceImpl(PaymentRepository paymentRepo,
                              BookingClient bookingClient,
                              RentalCompanyClient companyClient) {
        this.paymentRepo = paymentRepo;
        this.bookingClient = bookingClient;
        this.companyClient = companyClient;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        // fetch booking and company for validation (can be used later)
        bookingClient.getBookingById(getBookingId());
        companyClient.getCompanyById(request.getCompanyId());

        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setCompanyId(request.getCompanyId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setGatewayTransactionId(request.getGatewayTransactionId());
        payment.setStatus("completed");

        paymentRepo.save(payment);

        PaymentResponse response = new PaymentResponse();
        response.setStatus("success");
        response.setMessage("Payment processed successfully");

        return response;
    }

    @Override
    public Payment getPaymentByBookingId(Integer bookingId) {
        return paymentRepo.findById(bookingId).orElseThrow();
    }
}
