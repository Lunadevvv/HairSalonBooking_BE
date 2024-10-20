package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.BookingRequest;
import com.datvm.hairbookingapp.dto.request.BookingUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.BookingResponse;
import com.datvm.hairbookingapp.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping
    public ApiResponse<BookingResponse> createBooking(@RequestBody @Valid BookingRequest request){
        return ApiResponse.<BookingResponse>builder()
                .code(201)
                .message("Đặt lịch thành công!")
                .result(bookingService.createBooking(request))
                .build();
    }
    @PutMapping()
    public ApiResponse<BookingResponse> updateBooking(@RequestBody @Valid BookingUpdateRequest request){
        return ApiResponse.<BookingResponse>builder()
                .message("Dời lịch thành công!")
                .result(bookingService.updateBooking(request))
                .build();
    }
}
