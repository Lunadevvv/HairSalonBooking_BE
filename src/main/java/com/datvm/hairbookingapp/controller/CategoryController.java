package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.CategoryCreationRequest;
import com.datvm.hairbookingapp.dto.request.CategoryUpdateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.CategoryResponse;
import com.datvm.hairbookingapp.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryCreationRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.findCategoryById(id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<CategoryResponse>> getAllCategories(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.findAllCategory())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategoryById(@PathVariable("id") Long id,@RequestBody @Valid CategoryUpdateRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategoryById(id,request))
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategoryById(@PathVariable("id") Long id){
        categoryService.deleteCategoryById(id);
        return ApiResponse.<String>builder()
                .result("Category has been deleted")
                .build();
    }
}