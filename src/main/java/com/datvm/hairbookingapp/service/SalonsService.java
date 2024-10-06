package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.entity.Salons;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalonsService {
    Salons createSalons(Salons salon);
    Salons findSalonsBySalonId(Long salonId);
    List<Salons> findAllSalons();
    int updateSalonsBySalonId(Long salonId, Salons salon);
    void deleteSalonsBySalonId(Long salonId);
}
