package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.ServicesCreationRequest;
import com.datvm.hairbookingapp.dto.request.ServicesUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.ServicesResponse;
import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.entity.Services;
import com.datvm.hairbookingapp.service.ServicesService;
import jakarta.validation.Valid;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;

    @PostMapping("/{categoryId}")
    public ApiResponse<ServicesResponse> createService(@RequestBody @Valid ServicesCreationRequest service , @PathVariable("categoryId") Long categoryId) {
        return ApiResponse.<ServicesResponse>builder()
                .result(servicesService.createService(service, categoryId))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ServicesResponse> findServicesById(@PathVariable("id") Long id) {
        return ApiResponse.<ServicesResponse>builder()
                .result(servicesService.findByServiceId(id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<ServicesResponse>> getAllServices() {
        return ApiResponse.<List<ServicesResponse>>builder()
                .result(servicesService.findAllServices())
                .build();
    }

    @PutMapping("/{id}/category/{categoryId}")
    public ApiResponse<ServicesResponse> updateServiceById(@RequestBody @Valid ServicesUpdateRequest service, @PathVariable("categoryId") Long categoryId, @PathVariable("id") Long serviceId) {
        return ApiResponse.<ServicesResponse>builder()
                .result(servicesService.updateByServiceId(service,serviceId,categoryId))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteServiceById(@PathVariable("id") Long id) {
        servicesService.deleteByServiceId(id);
        return ApiResponse.<String>builder()
                .result("Services has been deleted")
                .build();
    }
}
