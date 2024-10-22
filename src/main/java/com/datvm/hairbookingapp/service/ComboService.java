package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.ComboCreationRequest;
import com.datvm.hairbookingapp.dto.response.ComboInfoResponse;
import com.datvm.hairbookingapp.entity.Combo;
import com.datvm.hairbookingapp.entity.Services;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.ComboMapper;
import com.datvm.hairbookingapp.repository.ComboRepository;
import com.datvm.hairbookingapp.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComboService {

    @Autowired
    ComboRepository comboRepository;

    @Autowired
    ServicesRepository servicesRepository;

    @Autowired
    ComboMapper comboMapper;

    public List<Combo> getAllCombos(){
        return comboRepository.findAll();
    }

    public Combo getCombo(Long id){
        Combo combo = comboRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMBO_NOT_FOUND));
        return combo;
    }

    public ComboInfoResponse createCombo(ComboCreationRequest request){
        List<Services> list = new ArrayList<>();
        for (String serviceId : request.getListServiceId()) {
            var service = servicesRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.SERVICES_NOT_EXISTED));
            list.add(service);
        }

        Combo combo = new Combo();
        combo.setServices(list);
        combo.setName(request.getName());
        combo.setPrice(request.getPrice());
        combo.setDescription(request.getDescription());
        combo = comboRepository.save(combo);

        ComboInfoResponse res = new ComboInfoResponse();
        res.setId(combo.getId());
        res.setName(combo.getName());
        res.setListServices(combo.getServices());
        res.setPrice(combo.getPrice());
        res.setDescription(combo.getDescription());
        return res;
    }

    public Combo updateCombo(Long id, ComboCreationRequest request){
        Combo combo = comboRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMBO_NOT_FOUND));
        List<Services> list = new ArrayList<>();
        for (String serviceId : request.getListServiceId()) {
            var service = servicesRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.SERVICES_NOT_EXISTED));
            list.add(service);
        }
        combo.setName(request.getName());
        combo.setServices(list);
        combo.setDescription(request.getDescription());
        combo.setPrice(request.getPrice());
        return comboRepository.save(combo);
    }

    public void deleteCombo(Long id){
        Combo combo = comboRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMBO_NOT_FOUND));
        // Remove the associations
        for (Services service : combo.getServices()) {
            service.getCombos().remove(combo); // Remove this combo from each service's list
        }
        combo.getServices().clear(); // Clear the services list in the combo

        comboRepository.delete(combo);
    }
}
