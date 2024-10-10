package com.datvm.hairbookingapp.service.impl;

import com.datvm.hairbookingapp.dto.request.CategoryCreationRequest;
import com.datvm.hairbookingapp.dto.request.CategoryUpdateRequest;
import com.datvm.hairbookingapp.dto.response.CategoryResponse;
import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.mapper.CategoryMapper;
import com.datvm.hairbookingapp.repository.CategoryRepository;
import com.datvm.hairbookingapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.datvm.hairbookingapp.exception.ErrorCode.*;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryCreationRequest request) {
        if (categoryRepository.existsById(request.getCategoryId())) throw new AppException(CATEGORY_EXISTED);
        Category category = categoryMapper.toCategory(request);
        // can't map from 3 entities to 4 entities
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse findCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) throw new AppException(CATEGORY_NOT_EXISTED);
        return categoryMapper.toCategoryResponse(categoryRepository.findByCategoryId(id));
    }

    @Override
    public List<CategoryResponse> findAllCategory() {

        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();

    }

    @Override
    public CategoryResponse updateCategoryById(Long id, CategoryUpdateRequest request) {
        if (!categoryRepository.existsById(id)) throw new AppException(CATEGORY_NOT_EXISTED);
        Category category = categoryRepository.findByCategoryId(id);
        categoryMapper.updateCategory(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) throw new AppException(CATEGORY_NOT_EXISTED);
        categoryRepository.deleteByCategoryId(id);
    }

}
