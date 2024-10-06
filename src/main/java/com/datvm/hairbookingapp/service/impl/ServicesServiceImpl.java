package com.datvm.hairbookingapp.service.impl;

import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.entity.Services;

import com.datvm.hairbookingapp.repository.CategoryRepository;
import com.datvm.hairbookingapp.repository.ServicesRepository;
import com.datvm.hairbookingapp.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServicesServiceImpl implements ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override

    public Services createService(Services service, Long categoryId) {
        Category category = categoryRepository.findCategoryByCategoryId(categoryId);
        service.setCategories(category);

        return servicesRepository.save(service);
    }

    public Services findServicesByServiceId(Long id){
        return servicesRepository.findServicesByServiceId(id);
    }

    @Override
    public List<Services> getAllServices() {
        return servicesRepository.findAll();
    }

    @Override
    public int updateServicesByServiceId(Services service, Long categoryId, Long serviceId) {
        Category category = categoryRepository.findCategoryByCategoryId(categoryId);
        return servicesRepository.updateServicesByServiceId(serviceId,service.getServiceName()
                , service.getDescription(), service.getPrice(), service.getDuration(), category);
    }

    @Override
    public void deleteServicesByServiceId(Long id) {
        servicesRepository.deleteServiceByServiceId(id);
    }
}
