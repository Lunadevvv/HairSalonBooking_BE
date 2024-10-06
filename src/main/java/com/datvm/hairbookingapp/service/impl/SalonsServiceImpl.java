package com.datvm.hairbookingapp.service.impl;

import com.datvm.hairbookingapp.entity.Salons;
import com.datvm.hairbookingapp.repository.SalonsRepository;
import com.datvm.hairbookingapp.service.SalonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SalonsServiceImpl implements SalonsService {
    @Autowired
    SalonsRepository salonsRepository;
    @Override
    public Salons createSalons(Salons salon) {
        return salonsRepository.save(salon);
    }

    @Override
    public Salons findSalonsBySalonId(Long salonId) {
        return salonsRepository.findSalonsBySalonId(salonId);
    }

    @Override
    public List<Salons> findAllSalons() {
        return salonsRepository.findAll();
    }

    @Override
    public int updateSalonsBySalonId(Long salonId, Salons salon) {
        String salonAddress = salon.getSalonAddress();
        String salonName = salon.getSalonName();
        String description = salon.getDescription();
        String openingHours = salon.getOpeningHours();
        String phoneNumber = salon.getPhoneNumber();
        return salonsRepository.updateSalonsBySalonId(salonId, salonAddress, salonName, description,
                                                      openingHours, phoneNumber);
    }

    @Override
    public void deleteSalonsBySalonId(Long salonId) {
        salonsRepository.deleteSalonsBySalonId(salonId);
    }
}
