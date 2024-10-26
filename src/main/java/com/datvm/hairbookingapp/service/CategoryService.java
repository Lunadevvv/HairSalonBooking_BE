package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.CategoryCreationRequest;
import com.datvm.hairbookingapp.dto.request.CategoryUpdateRequest;
import com.datvm.hairbookingapp.dto.response.CategoryResponse;
import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.entity.Services;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.mapper.CategoryMapper;
import com.datvm.hairbookingapp.repository.CategoryRepository;
import com.datvm.hairbookingapp.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.datvm.hairbookingapp.exception.ErrorCode.*;


@Service
public class CategoryService  {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ServicesRepository servicesRepository;

    public CategoryResponse createCategory(CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);
        // can't map from 3 entities to 4 entities
        category.setCategoryId(generateCategoryId());
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public CategoryResponse findCategoryById(String id) {
        if (!categoryRepository.existsById(id)) throw new AppException(CATEGORY_NOT_EXISTED);
        return categoryMapper.toCategoryResponse(categoryRepository.findByCategoryId(id));
    }

    public List<CategoryResponse> findAllCategory() {

        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();

    }

    public CategoryResponse updateCategoryById(CategoryUpdateRequest request) {
        if (!categoryRepository.existsById(request.getCategoryId())) throw new AppException(CATEGORY_NOT_EXISTED);
        Category category = categoryRepository.findByCategoryId(request.getCategoryId());
        categoryMapper.updateCategory(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void deleteCategoryById(String id) {
        if (!categoryRepository.existsById(id)) throw new AppException(CATEGORY_NOT_EXISTED);
        Category category = categoryRepository.findByCategoryId(id);
        List<Services> services = category.getService();
        for (Services service : services) {
            service.setCategories(null);
        }
        category.setService(null);
        categoryRepository.save(category);
        categoryRepository.deleteByCategoryId(id);
    }


    public String generateCategoryId() {
        String id = "CT0001";
        String lastId = categoryRepository.findLastId();
        if(lastId == null)
            return id;
        int fourLastNumber = Integer.parseInt(lastId.substring(2));
        id = String.format("CT%04d", ++fourLastNumber);
        return id;
    }
}
