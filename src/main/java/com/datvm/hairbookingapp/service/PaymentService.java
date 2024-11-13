package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.BookingRequest;
import com.datvm.hairbookingapp.dto.response.PaymentResponse;
import com.datvm.hairbookingapp.entity.*;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.entity.enums.FeedbackStatus;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.PaymentMapper;

import com.datvm.hairbookingapp.repository.FeedbackRepository;

import com.datvm.hairbookingapp.repository.AuthenticationRepository;

import com.datvm.hairbookingapp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private AuthenticationRepository accountRepository;

    public PaymentResponse submitPayment(String id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        Booking booking = payment.getBooking();
        if (booking.getStatus() == BookingStatus.SUCCESS) {
            Account account = booking.getAccount();
            int shinePoint = account.getShinePoint();
            int pointAdd = booking.getPrice()/1000;
            account.setShinePoint(shinePoint+pointAdd);
            accountRepository.save(account);
            bookingService.updateBookingStatus(booking.getId(), BookingStatus.COMPLETED);
            Feedback feedback = booking.getFeedback();
            feedback.setStatus(FeedbackStatus.OPEN);
            feedbackRepository.save(feedback);

            if(booking.getPeriod() != 0){
                createNewBooking(booking);
            }
        } else if (booking.getStatus() == BookingStatus.COMPLETED)
            throw new AppException(ErrorCode.PAYMENT_ALREADY_DONE);
        else throw new AppException(ErrorCode.PAYMENT_INVALID_SUBMIT);
        return paymentMapper.toPaymentResponse(payment);
    }

    public void createNewBooking(Booking booking){
        BookingRequest request = new BookingRequest();

        request.setSalonId(booking.getSalonId());
        request.setDate(booking.getDate().plusDays(7L * booking.getPeriod()));
        request.setStylistId(booking.getStylistId().getCode());
        request.setSlotId(booking.getSlot().getId());
        request.setPrice(booking.getPrice());
        request.setPeriod(booking.getPeriod());

        List<String> listServices = new ArrayList<>();
        List<Services> services = booking.getServices();
        for(Services s: services){
            listServices.add(s.getServiceId());
        }
        request.setServiceId(listServices);

        Account account = booking.getAccount();

        bookingService.createBooking(request, account);
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
