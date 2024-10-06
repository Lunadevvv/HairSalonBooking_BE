package com.datvm.hairbookingapp.service.impl;

import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.repository.CategoryRepository;
import com.datvm.hairbookingapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findCategoryByCategoryId(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public int updateCategoryById(Long id, Category category) {
        String name = category.getCategoryName();
        String description = category.getCategoryDescription();
        return categoryRepository.updateCategoryById(id,name,description);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteCategoryByCategoryId(id);
    }

    @Override
    public List<Category> getCategoryByName(String name) {
        return categoryRepository.findByCategoryNameContaining(name);
    }
}
