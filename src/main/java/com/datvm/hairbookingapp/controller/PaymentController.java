package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.PaymentResponse;
import com.datvm.hairbookingapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{id}")
    public ApiResponse<PaymentResponse> submitPayment(@PathVariable String id) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.submitPayment(id))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PaymentResponse> findPaymentById(@PathVariable String id) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.findPaymentById(id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<PaymentResponse>> getAllPaymentsComplete() {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getAllPaymentsAlreadyComplete())
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<PaymentResponse>> getAllPayments() {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getAllPayments())
                .build();
    }
}
