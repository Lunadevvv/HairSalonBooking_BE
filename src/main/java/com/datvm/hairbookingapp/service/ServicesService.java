package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.ServicesCreationRequest;
import com.datvm.hairbookingapp.dto.request.ServicesUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.ServicesResponse;
import com.datvm.hairbookingapp.entity.Services;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface ServicesService {
    ServicesResponse createService(ServicesCreationRequest request, String categoryId);
    ServicesResponse findByServiceId(String serviceId);
    List<ServicesResponse> findAllServices();
    ServicesResponse updateByServiceId(ServicesUpdateRequest request, String ServiceId, String categoryId);
    void deleteByServiceId(String serviceId);
    String generateServiceId();
}
