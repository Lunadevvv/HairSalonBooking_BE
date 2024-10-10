package com.datvm.hairbookingapp.service.impl;

import com.datvm.hairbookingapp.dto.request.SalonCreationRequest;
import com.datvm.hairbookingapp.dto.request.SalonUpdateRequest;
import com.datvm.hairbookingapp.dto.response.SalonResponse;
import com.datvm.hairbookingapp.entity.Salon;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.mapper.SalonMapper;
import com.datvm.hairbookingapp.repository.SalonRepository;
import com.datvm.hairbookingapp.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.datvm.hairbookingapp.exception.ErrorCode.*;

@Service
public class SalonServiceImpl implements SalonService {
    @Autowired
    private SalonRepository salonRepository;
    @Autowired
    private SalonMapper salonMapper;


    @Override
    public SalonResponse createSalon(SalonCreationRequest request) {
        if (salonRepository.existsById(request.getSalonId()))
            throw new AppException(SALON_EXISTED);
        Salon salon = salonMapper.toSalon(request);
        try {
            return salonMapper.toSalonResponse(salonRepository.save(salon));
        } catch (Exception e) {
            throw new AppException(DUPLICATE_PHONE);
        }
    }

    @Override
    public SalonResponse findSalonById(Long salonId) {
        if (!salonRepository.existsById(salonId)) throw new AppException(SALON_NOT_EXISTED);
        return salonMapper.toSalonResponse(salonRepository.findBySalonId(salonId));
    }

    @Override
    public List<SalonResponse> findAllSalon() {
        return salonRepository.findAll().stream().map(salonMapper::toSalonResponse).toList();
        //chuyển đổi List thành stream để mapping
        //truy cập vào lớp salonMapper sử dụng phương thức để map từng phần tử
        //toList() chuyển đổi thành List
    }

    @Override
    public SalonResponse updateSalonById(Long salonId, SalonUpdateRequest request) {
        if (!salonRepository.existsById(salonId)) throw new AppException(SALON_NOT_EXISTED);
        Salon salon = salonRepository.findBySalonId(salonId);
        salonMapper.updateSalon(salon, request);
        try {
            return salonMapper.toSalonResponse(salonRepository.save(salon));
        } catch (Exception e) {
            throw new AppException(DUPLICATE_PHONE);
        }
    }

    @Override
    public void deleteSalonById(Long salonId) {
        if (!salonRepository.existsById(salonId)) throw new AppException(SALON_NOT_EXISTED);
        salonRepository.deleteBySalonId(salonId);
    }
}
