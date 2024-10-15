package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.request.ComboCreationRequest;
import com.datvm.hairbookingapp.dto.response.ComboInfoResponse;
import com.datvm.hairbookingapp.entity.Combo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComboMapper {
    ComboInfoResponse toComboRes(Combo combo);
}
