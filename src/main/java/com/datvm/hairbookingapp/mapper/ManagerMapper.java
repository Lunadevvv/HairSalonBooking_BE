package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.response.ManagerResponse;
import com.datvm.hairbookingapp.entity.Manager;
import org.mapstruct.Mapper;

@Mapper
public interface ManagerMapper {
    ManagerResponse toManagerResponse(Manager manager);
}
