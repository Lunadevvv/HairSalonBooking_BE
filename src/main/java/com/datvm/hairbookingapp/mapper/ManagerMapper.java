package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.response.ManagerResponse;
import com.datvm.hairbookingapp.entity.Manager;
import com.datvm.hairbookingapp.repository.ManagerRepository;
import org.mapstruct.Mapper;

@Mapper
public interface ManagerMapper {
    ManagerResponse toManagerResponse(Manager manager);
}
