package com.datvm.hairbookingapp.service;


import com.datvm.hairbookingapp.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    int updateCategoryById(Long id, Category category);
    void deleteCategoryById(Long id);
    List<Category> getCategoryByName(String name);
}
