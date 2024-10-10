package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.request.ServicesCreationRequest;
import com.datvm.hairbookingapp.dto.request.ServicesUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ServicesResponse;
import com.datvm.hairbookingapp.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface ServicesMapper {
    Services toServices(ServicesCreationRequest request);
    ServicesResponse toServicesResponse(Services services);
    void updateServices(@MappingTarget Services service, ServicesUpdateRequest request);
}
