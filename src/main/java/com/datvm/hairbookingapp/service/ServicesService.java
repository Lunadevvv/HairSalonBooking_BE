package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.entity.Services;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Service
public interface ServicesService {
    Services createService(Services services, Long categoryId);
    List<Services> getAllServices();
    int updateServicesByServiceId(Services service, Long categoryId, Long serviceId);
    void deleteServicesByServiceId(Long id);
    Services findServicesByServiceId(Long id);
}
