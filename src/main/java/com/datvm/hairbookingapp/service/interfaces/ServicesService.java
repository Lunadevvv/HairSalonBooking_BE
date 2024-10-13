package com.datvm.hairbookingapp.service.interfaces;

import com.datvm.hairbookingapp.dto.request.ServicesCreationRequest;
import com.datvm.hairbookingapp.dto.request.ServicesUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ServicesResponse;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface ServicesService {
    ServicesResponse createService(ServicesCreationRequest request);
    ServicesResponse findByServiceId(String serviceId);
    List<ServicesResponse> findAllServices();
    ServicesResponse updateByServiceId(ServicesUpdateRequest request);
    void deleteByServiceId(String serviceId);
    String generateServiceId();
}
