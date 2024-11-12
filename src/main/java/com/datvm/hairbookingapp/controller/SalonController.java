package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.SalonCreationRequest;
import com.datvm.hairbookingapp.dto.request.SalonUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.SalonResponse;
import com.datvm.hairbookingapp.service.SalonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/salon")
public class SalonController {
    @Autowired
    SalonService salonService;

    @PostMapping
    public ApiResponse<SalonResponse> CreateSalon(@Valid @RequestBody SalonCreationRequest request) {
        return ApiResponse.<SalonResponse>builder()
                .message("Salon được tạo thành công")
                .result(salonService.CreateSalon(request))
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<SalonResponse> getSalon(@PathVariable String id) {
        return ApiResponse.<SalonResponse>builder()
                .result(salonService.findById(id))
                .build();
    }
    @GetMapping
    public ApiResponse<List<SalonResponse>> getAllSalon() {
        return ApiResponse.<List<SalonResponse>>builder()
                .result(salonService.getAllSalons())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SalonResponse> updateSalon(@Valid @RequestBody SalonUpdateRequest request, @PathVariable String id) {
        return  ApiResponse.<SalonResponse>builder()
                .result(salonService.UpdateSalon(request, id))
                .build();
    }

    @PutMapping("/{id}/status")
    public ApiResponse<SalonResponse> updateSalonStatus(@PathVariable String id) {
        return  ApiResponse.<SalonResponse>builder()
                .result(salonService.UpdateSalonStatus(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSalon(@PathVariable String id) {
        return ApiResponse.<String>builder()
                .result(salonService.deleteSalon(id))
                .build();
    }
}
