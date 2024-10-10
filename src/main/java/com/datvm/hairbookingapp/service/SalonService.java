package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.SalonCreationRequest;
import com.datvm.hairbookingapp.dto.request.SalonUpdateRequest;
import com.datvm.hairbookingapp.dto.response.SalonResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface SalonService {
    SalonResponse createSalon(SalonCreationRequest request);
    SalonResponse findSalonBySalonId(Long salonId);
    List<SalonResponse> findAllSalon();
    SalonResponse updateSalonBySalonId(Long salonId, SalonUpdateRequest request);
    void deleteSalonBySalonId(Long salonId);
}
