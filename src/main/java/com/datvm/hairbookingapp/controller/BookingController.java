package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.BookingRequest;
import com.datvm.hairbookingapp.dto.request.BookingUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.BookingResponse;
import com.datvm.hairbookingapp.entity.Booking;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @GetMapping
    public ApiResponse<List<Booking>> getAllBookings() {
        return ApiResponse.<List<Booking>>builder()
                .result(bookingService.getBookings())
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
    public ApiResponse<String> deleteBooking(@PathVariable String id){
        return ApiResponse.<String>builder()
                .result(bookingService.deleteBooking(id))
                .build();
    }
}
