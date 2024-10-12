package com.datvm.hairbookingapp.service;


import com.datvm.hairbookingapp.dto.request.CategoryCreationRequest;
import com.datvm.hairbookingapp.dto.request.CategoryUpdateRequest;
import com.datvm.hairbookingapp.dto.response.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryResponse createCategory(CategoryCreationRequest request);
    CategoryResponse findCategoryById(Long id);
    List<CategoryResponse> findAllCategory();
    CategoryResponse updateCategoryById(Long id, CategoryUpdateRequest request);
    void deleteCategoryById(Long id);
}
