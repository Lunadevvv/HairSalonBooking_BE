package com.datvm.hairbookingapp.service.impl;

import com.datvm.hairbookingapp.dto.request.ServicesCreationRequest;
import com.datvm.hairbookingapp.dto.request.ServicesUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ServicesResponse;
import com.datvm.hairbookingapp.entity.Services;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.mapper.ServicesMapper;
import com.datvm.hairbookingapp.repository.CategoryRepository;
import com.datvm.hairbookingapp.repository.ServicesRepository;
import com.datvm.hairbookingapp.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.datvm.hairbookingapp.exception.ErrorCode.*;

@Service
public class ServicesServiceImpl implements ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private ServicesMapper servicesMapper;

    @Override
    public ServicesResponse createService(ServicesCreationRequest request, Long categoryId) {
        if(servicesRepository.existsById(request.getServiceId())) throw new AppException(SERVICES_EXISTED);
        if(!categoryRepository.existsById(categoryId)) throw new AppException(CATEGORY_NOT_EXISTED);
        request.setCategories(categoryRepository.findByCategoryId(categoryId));
        Services service = servicesMapper.toServices(request);
        return servicesMapper.toServicesResponse(servicesRepository.save(service));
    }

    @Override
    public ServicesResponse findByServiceId(Long id) {
        if(!servicesRepository.existsById(id)) throw new AppException(SERVICES_NOT_EXISTED);
        return servicesMapper.toServicesResponse(servicesRepository.findByServiceId(id));
    }

    @Override
    public List<ServicesResponse> findAllServices() {
        return servicesRepository.findAll().stream().map(servicesMapper::toServicesResponse).toList();
    }

    @Override
    public ServicesResponse updateByServiceId(ServicesUpdateRequest request, Long serviceId, Long categoryId) {
        if(!servicesRepository.existsById(serviceId)) throw new AppException(SERVICES_NOT_EXISTED);
        if(!categoryRepository.existsById(categoryId)) throw new AppException(CATEGORY_NOT_EXISTED);
        Services service = servicesRepository.findByServiceId(serviceId);
        servicesMapper.updateServices(service, request);
        service.setCategories(categoryRepository.findByCategoryId(categoryId));
        System.out.println("this is service" + service);
        return servicesMapper.toServicesResponse(servicesRepository.save(service));
    }

    @Override
    public void deleteByServiceId(Long serviceId) {
        if(!servicesRepository.existsById(serviceId)) throw new AppException(SERVICES_NOT_EXISTED);
        servicesRepository.deleteById(serviceId);
    }
}
