package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.ServicesCreationRequest;
import com.datvm.hairbookingapp.dto.request.ServicesUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ServicesResponse;
import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.entity.Combo;
import com.datvm.hairbookingapp.entity.Services;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.mapper.ServicesMapper;
import com.datvm.hairbookingapp.repository.CategoryRepository;
import com.datvm.hairbookingapp.repository.ComboRepository;
import com.datvm.hairbookingapp.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.datvm.hairbookingapp.exception.ErrorCode.*;

@Service
public class ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private ServicesMapper servicesMapper;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private ComboService comboService;

    public ServicesResponse createService(ServicesCreationRequest request) {
        if (!categoryRepository.existsById(request.getCategoryId()))
            throw new AppException(CATEGORY_NOT_EXISTED);
        if (!request.getImage().contains("imgur"))
            throw new AppException(INVALID_IMAGE);
        Services service = servicesMapper.toServices(request);
        service.setServiceId(generateServiceId());
        service.setStatus(true);
        Category category = categoryRepository.findByCategoryId(request.getCategoryId());
        service.setCategories(category);
        return servicesMapper.toServicesResponse(servicesRepository.save(service));
    }


    public ServicesResponse findByServiceId(String id) {
        return servicesMapper.toServicesResponse(servicesRepository.findById(id).orElseThrow(() -> new AppException(SERVICES_NOT_EXISTED)));
    }

    public List<ServicesResponse> findAllService() {
        return servicesRepository.findAll().stream().map(servicesMapper::toServicesResponse).toList();
    }

    public ServicesResponse updateByServiceId(ServicesUpdateRequest request) {
        if (!categoryRepository.existsById(request.getCategoryId())) throw new AppException(CATEGORY_NOT_EXISTED);
        if (!request.getImage().contains("imgur"))
            throw new AppException(INVALID_IMAGE);
        Services service = servicesRepository.findById(request.getServiceId()).orElseThrow(() -> new AppException(SERVICES_NOT_EXISTED));
        servicesMapper.updateServices(service, request);
        service.setCategories(categoryRepository.findByCategoryId(request.getCategoryId()));
        return servicesMapper.toServicesResponse(servicesRepository.save(service));
    }

    public void switchServiceStatus(String serviceId) {
        boolean flag = false;
        Services service = servicesRepository.findById(serviceId).orElseThrow(() -> new AppException(SERVICES_NOT_EXISTED));
        System.out.println("current serviceIsStatus: "+service.isStatus());
        boolean newStatus = !service.isStatus();
        service.setStatus(newStatus);
        System.out.println("updated service.getStatus: "+service.isStatus());
        if(!newStatus && service.getCombos()!= null){
            List<Combo> combos = service.getCombos();
            for (Combo combo : combos) {
                if(combo.getServices().size() == 2){
                    flag = true;
                    comboService.deleteCombo(combo.getId());
                }else{
                    combo.getServices().remove(service);
                    combo.setPrice(comboService.setComboPrice(combo.getServices()));
                    comboRepository.save(combo);
                }
            }
//            if(!flag)
            service.setCombos(null);
        }
        servicesRepository.save(service);
    }

    public String generateServiceId() {
        String id = "SV0001";
        String lastId = servicesRepository.findLastId();
        if (lastId == null)
            return id;
        int fourLastNumber = Integer.parseInt(lastId.substring(2));
        id = String.format("SV%04d", ++fourLastNumber);
        return id;
    }
}
