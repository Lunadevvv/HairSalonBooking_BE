package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.ServicesCreationRequest;
import com.datvm.hairbookingapp.dto.request.ServicesUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.ServicesResponse;
import com.datvm.hairbookingapp.service.interfaces.ServicesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;

    @PostMapping()
    public ApiResponse<ServicesResponse> createService(@RequestBody @Valid ServicesCreationRequest service) {
        return ApiResponse.<ServicesResponse>builder()
                .result(servicesService.createService(service))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ServicesResponse> findServicesById(@PathVariable("id") String id) {
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

    @PutMapping()
    public ApiResponse<ServicesResponse> updateServiceById(@RequestBody @Valid ServicesUpdateRequest service) {
        return ApiResponse.<ServicesResponse>builder()
                .result(servicesService.updateByServiceId(service))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteServiceById(@PathVariable("id") String id) {
        servicesService.deleteByServiceId(id);
        return ApiResponse.<String>builder()
                .result("Services has been deleted")
                .build();
    }
}
