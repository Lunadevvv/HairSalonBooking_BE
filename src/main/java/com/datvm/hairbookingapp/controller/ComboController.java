package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.ComboCreationRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.ComboInfoResponse;
import com.datvm.hairbookingapp.entity.Combo;
import com.datvm.hairbookingapp.service.ComboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/combo")
public class ComboController {

    @Autowired
    ComboService comboService;

    @GetMapping
    public ApiResponse<List<Combo>> getAllCombo(){
        return ApiResponse.<List<Combo>>builder()
                .code(200)
                .result(comboService.getAllCombos())
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<Combo> getComboById(@PathVariable Long id){
        return ApiResponse.<Combo>builder()
                .code(200)
                .result(comboService.getCombo(id))
                .build();
    }


    @PostMapping
    public ApiResponse<ComboInfoResponse> createCombo(@RequestBody @Valid ComboCreationRequest request){
        return ApiResponse.<ComboInfoResponse>builder()
                .code(200)
                .result(comboService.createCombo(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Combo> updateCombo(@RequestBody @Valid ComboCreationRequest request, @PathVariable Long id){
        return ApiResponse.<Combo>builder()
                .code(200)
                .message("Update ComboId: " + id + " thành công!")
                .result(comboService.updateCombo(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteCombo(@PathVariable Long id){
        comboService.deleteCombo(id);
        return ApiResponse.builder()
                .code(200)
                .message("Delete ComboId: " + id + " thành công!")
                .build();
    }
}
