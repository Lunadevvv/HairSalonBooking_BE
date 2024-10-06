package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @PostMapping("/create")
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }
    @GetMapping("/get")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @GetMapping(value = "/find",params = "id")
    public Category getCategoryById(@RequestParam("id") Long id){
        return categoryService.getCategoryById(id);
    }
    @GetMapping(value = "/find", params = "name")
    public List<Category> getCategoryByName(@RequestParam("name") String name){
        return categoryService.getCategoryByName(name);
    }
    @PutMapping("/update")
    public int updateCategoryById(@RequestParam("id") Long id,@RequestBody Category category){
        return categoryService.updateCategoryById(id, category);
    }
    @DeleteMapping("/delete")
    public void deleteCategoryById(@RequestParam("id") Long id){
        categoryService.deleteCategoryById(id);
    }
}
