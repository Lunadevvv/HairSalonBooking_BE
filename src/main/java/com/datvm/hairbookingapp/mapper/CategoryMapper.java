package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.request.CategoryCreationRequest;
import com.datvm.hairbookingapp.dto.request.CategoryUpdateRequest;
import com.datvm.hairbookingapp.dto.response.CategoryResponse;
import com.datvm.hairbookingapp.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest CategoryCreationRequest);
    CategoryResponse toCategoryResponse(Category response);
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest CategoryUpdateRequest);
}
