package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.response.PaymentResponse;
import com.datvm.hairbookingapp.entity.Booking;
import com.datvm.hairbookingapp.entity.Payment;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.PaymentMapper;
import com.datvm.hairbookingapp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookingService bookingService;

    public PaymentResponse submitPayment(String id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        Booking booking = payment.getBooking();
        if (booking.getStatus() == BookingStatus.SUCCESS) {
            bookingService.updateBookingStatus(booking.getId(), BookingStatus.COMPLETED);
        } else if (booking.getStatus() == BookingStatus.COMPLETED)
            throw new AppException(ErrorCode.PAYMENT_ALREADY_DONE);
        else throw new AppException(ErrorCode.PAYMENT_INVALID_SUBMIT);
        return paymentMapper.toPaymentResponse(payment);
    }

    public PaymentResponse findPaymentById(String id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        return paymentMapper.toPaymentResponse(payment);
    }

    public List<PaymentResponse> getAllPaymentsAlreadyComplete() {
        List<Payment> payments = paymentRepository.findAllPaymentAlreadyComplete(BookingStatus.COMPLETED);
        return payments.stream().map(paymentMapper::toPaymentResponse).toList();
    }
}
