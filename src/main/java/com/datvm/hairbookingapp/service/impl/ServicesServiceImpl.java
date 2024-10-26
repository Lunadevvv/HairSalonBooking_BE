package com.datvm.hairbookingapp.service.impl;

import com.datvm.hairbookingapp.dto.request.ServicesCreationRequest;
import com.datvm.hairbookingapp.dto.request.ServicesUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ServicesResponse;
import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.entity.Services;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.mapper.ServicesMapper;
import com.datvm.hairbookingapp.repository.CategoryRepository;
import com.datvm.hairbookingapp.repository.ServicesRepository;
import com.datvm.hairbookingapp.service.interfaces.ServicesService;
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
    public ServicesResponse createService(ServicesCreationRequest request) {
        if(!categoryRepository.existsById(request.getCategoryId()))
            throw new AppException(CATEGORY_NOT_EXISTED);
        if(!request.getImage().contains("imgur"))
            throw new AppException(INVALID_IMAGE);
        Services service = new Services();
        service.setDescription(request.getDescription());
        service.setServiceName(request.getServiceName());
        service.setImage(request.getImage());
        service.setDuration(request.getDuration());
        service.setPrice(request.getPrice());
        service.setServiceId(generateServiceId());
        Category category = categoryRepository.findByCategoryId(request.getCategoryId());
        service.setCategories(category);
        service = servicesRepository.save(service);
        return ServicesResponse.builder()
                .serviceName(service.getServiceName())
                .image(service.getImage())
                .categories(service.getCategories())
                .description(service.getDescription())
                .duration(service.getDuration())
                .price(service.getPrice())
                .serviceId(service.getServiceId())
                .build();
    }

    @Override
    public ServicesResponse findByServiceId(String id) {
        return servicesMapper.toServicesResponse(servicesRepository.findById(id).orElseThrow(() -> new AppException(SERVICES_NOT_EXISTED)));
    }

    @Override
    public List<ServicesResponse> findAllServices() {
        return servicesRepository.findAll().stream().map(servicesMapper::toServicesResponse).toList();
    }

    @Override
    public ServicesResponse updateByServiceId(ServicesUpdateRequest request) {
        if(!categoryRepository.existsById(request.getCategoryId())) throw new AppException(CATEGORY_NOT_EXISTED);
        if(!request.getImage().contains("imgur"))
            throw new AppException(INVALID_IMAGE);
        Services service = servicesRepository.findById(request.getServiceId()).orElseThrow(() -> new AppException(SERVICES_NOT_EXISTED));
        servicesMapper.updateServices(service, request);
        service.setCategories(categoryRepository.findByCategoryId(request.getCategoryId()));
        return servicesMapper.toServicesResponse(servicesRepository.save(service));
    }

    @Override
    public void deleteByServiceId(String serviceId) {
        if(!servicesRepository.existsById(serviceId)) throw new AppException(SERVICES_NOT_EXISTED);
        servicesRepository.deleteById(serviceId);
    }

    @Override
    public String generateServiceId() {
        String id = "SV0001";
        String lastId = servicesRepository.findLastId();
        if(lastId == null)
            return id;
        int fourLastNumber = Integer.parseInt(lastId.substring(2));
        id = String.format("SV%04d", ++fourLastNumber);
        return id;
    }
}
