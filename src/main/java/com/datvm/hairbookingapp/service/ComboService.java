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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ComboService {

    @Autowired
    ComboRepository comboRepository;

    @Autowired
    ServicesRepository servicesRepository;

    @Autowired
    ComboMapper comboMapper;

    @Value("${app.comboPercent}")
    private double percent;

    public List<Combo> getAllCombos() {
        return comboRepository.findAll();
    }

    public Combo getCombo(Long id) {
        Combo combo = comboRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMBO_NOT_FOUND));
        return combo;
    }

    public ComboInfoResponse submitCreateCombo(ComboCreationRequest request) {
        List<Services> list = new ArrayList<>();

        // Retrieve services based on the provided IDs
        for (String serviceId : request.getListServiceId()) {
            var service = servicesRepository.findById(serviceId)
                    .orElseThrow(() -> new AppException(ErrorCode.SERVICES_NOT_EXISTED));
            if (!service.isStatus())
                throw new AppException(ErrorCode.SERVICES_NOT_ACTIVE);
            list.add(service);
        }

        // Sort the list of services for consistent comparison
        Collections.sort(list, Comparator.comparing(Services::getServiceId));

        // Check for existing combos
        List<Combo> combos = comboRepository.findAll();
        for (Combo combo : combos) {
            List<Services> existingServices = combo.getServices();
            Collections.sort(existingServices, Comparator.comparing(Services::getServiceId));

            // Check if the existing combo's services match the new list
            if (existingServices.size() == list.size()) {
                boolean match = true;
                for (int i = 0; i < existingServices.size(); i++) {
                    if (!existingServices.get(i).getServiceId().equals(list.get(i).getServiceId())) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    throw new AppException(ErrorCode.DUPLICATE_COMBO);
                }
            }
        }

        // If no duplicate found, create the new combo
        return createCombo(request, list);
    }

    public ComboInfoResponse createCombo(ComboCreationRequest request, List<Services> list) {
        Combo combo = new Combo();
        combo.setServices(list);
        combo.setName(request.getName());
        combo.setPrice(setComboPrice(list));
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

    public Combo updateCombo(Long id, ComboCreationRequest request) {
        Combo combo = comboRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMBO_NOT_FOUND));
        List<Services> list = new ArrayList<>();
        for (String serviceId : request.getListServiceId()) {
            var service = servicesRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.SERVICES_NOT_EXISTED));
            if (!service.isStatus())
                throw new AppException(ErrorCode.SERVICES_NOT_ACTIVE);
            list.add(service);
        }
        // Sort the list of services for consistent comparison
        Collections.sort(list, Comparator.comparing(Services::getServiceId));

        // Check for existing combos
        List<Combo> combos = comboRepository.findAllRemainCombo(id);
        for (Combo combo2 : combos) {
            List<Services> existingServices = combo2.getServices();
            Collections.sort(existingServices, Comparator.comparing(Services::getServiceId));

            // Check if the existing combo's services match the new list
            if (existingServices.size() == list.size()) {
                boolean match = true;
                for (int i = 0; i < existingServices.size(); i++) {
                    if (!existingServices.get(i).getServiceId().equals(list.get(i).getServiceId())) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    throw new AppException(ErrorCode.DUPLICATE_COMBO);
                }
            }
        }

        combo.setName(request.getName());
        combo.setServices(list);
        combo.setDescription(request.getDescription());
        combo.setPrice(setComboPrice(list));
        return comboRepository.save(combo);
    }

    public int setComboPrice(List<Services> services) {
        int price = 0;
        for (Services s : services) {
            price = price + s.getPrice();
        }
        return (int) Math.floor(price * (100 - percent) / 100);
    }

    public void deleteCombo(Long id) {
        Combo combo = comboRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMBO_NOT_FOUND));
        List<Services> services = combo.getServices();
        //xóa combo trong list combo chứa service
        for (Services service : services) {
            service.getCombos().remove(combo);
        }
        //set null để tránh xóa hết các service
        combo.setServices(null);
        comboRepository.save(combo);
        comboRepository.deleteById(id);
    }
}
