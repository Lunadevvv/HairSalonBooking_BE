package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.BookingRequest;
import com.datvm.hairbookingapp.dto.request.BookingUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.BookingResponse;
import com.datvm.hairbookingapp.dto.response.FeedbackResponse;
import com.datvm.hairbookingapp.dto.response.PaymentResponse;
import com.datvm.hairbookingapp.entity.Booking;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ApiResponse<List<Booking>> getAllBookings() {
        return ApiResponse.<List<Booking>>builder()
                .result(bookingService.getBookings())
                .build();
    }

    @PatchMapping()
    public ApiResponse<List<Booking>> getAccountBookings() {
        return ApiResponse.<List<Booking>>builder()
                .result(bookingService.getBookingsByAccount())
                .build();
    }

    @PostMapping
    public ApiResponse<BookingResponse> createBooking(@RequestBody @Valid BookingRequest request){
        return ApiResponse.<BookingResponse>builder()
                .code(201)
                .message("Đặt lịch thành công!")
                .result(bookingService.submitBooking(request))
                .build();
    }

    @PutMapping("/{id}/{status}")
    public ApiResponse updateStatus(@PathVariable String id, @PathVariable BookingStatus status){
        bookingService.updateBookingStatus(id, status);
        return ApiResponse.builder()
                .code(200)
                .message("Update thành công booking " + id + " thành " + status.toString())
                .build();
    }

    @PutMapping()
    public ApiResponse<BookingResponse> updateBookingSlot(@RequestBody @Valid BookingUpdateRequest request){
        return ApiResponse.<BookingResponse>builder()
                .message("Dời lịch thành công!")
                .result(bookingService.updateBookingSlot(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BookingResponse> CancelPeriodBooking(@PathVariable String id){
        return ApiResponse.<BookingResponse>builder()
                .message("Cancel thành công")
                .result(bookingService.cancelPeriodBooking(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> cancelBooking(@PathVariable String id){
        return ApiResponse.<String>builder()
                .result(bookingService.cancelBooking(id))
                .build();
    }

    @GetMapping("/manager")
    public ApiResponse<List<Booking>> getAllBookingsBySalon() {
        return ApiResponse.<List<Booking>>builder()
                .result(bookingService.getBookingsBySalon())
                .build();
    }
    @GetMapping("/payment/{id}")
    public ApiResponse<PaymentResponse> findPaymentByBookingId(@PathVariable String id) {
        return ApiResponse.<PaymentResponse>builder()
                .result(bookingService.findPaymentByBookingId(id))
                .build();
    }
    @GetMapping("/feedback/{id}")
    public ApiResponse<FeedbackResponse> findFeedbackByBookingId(@PathVariable String id) {
        return ApiResponse.<FeedbackResponse>builder()
                .result(bookingService.findFeedbackByBookingId(id))
                .build();
    }
}
