package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.request.SalonCreationRequest;
import com.datvm.hairbookingapp.dto.request.SalonUpdateRequest;
import com.datvm.hairbookingapp.dto.response.SalonResponse;
import com.datvm.hairbookingapp.entity.Salon;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface SalonMapper {
    Salon toSalon(SalonCreationRequest CategoryCreationRequest);
    SalonResponse toSalonResponse(Salon response);
    void updateSalon(@MappingTarget Salon salon, SalonUpdateRequest SalonUpdateRequest);
}
