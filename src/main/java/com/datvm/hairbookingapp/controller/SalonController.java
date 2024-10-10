package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.SalonCreationRequest;
import com.datvm.hairbookingapp.dto.request.SalonUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.SalonResponse;
import com.datvm.hairbookingapp.entity.Salon;
import com.datvm.hairbookingapp.service.SalonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salon")
public class SalonController {
    @Autowired
    private SalonService salonService;
    @PostMapping()
    public ApiResponse<SalonResponse> create(@Valid @RequestBody SalonCreationRequest request) {
        return ApiResponse.<SalonResponse>builder()
                .result(salonService.createSalon(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SalonResponse> findSalonsById(@PathVariable("id") Long id) {
        return ApiResponse.<SalonResponse>builder()
                .result(salonService.findSalonBySalonId(id))
                .build();
    }
    @GetMapping()
    public ApiResponse<List<SalonResponse>> getAllSalons() {
        return ApiResponse.<List<SalonResponse>>builder()
                .result(salonService.findAllSalon())
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<SalonResponse> updateSalonById(@RequestBody SalonUpdateRequest request, @PathVariable("id") Long id) {
        return ApiResponse.<SalonResponse>builder()
                .result(salonService.updateSalonBySalonId(id,request))
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSalonById(@PathVariable("id") Long id) {
        salonService.deleteSalonBySalonId(id);
        return ApiResponse.<String>builder()
                .result("Salon has been deleted")
                .build();
    }
}
