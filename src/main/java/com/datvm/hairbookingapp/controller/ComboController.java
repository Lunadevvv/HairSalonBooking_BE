package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.ComboCreationRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.ComboInfoResponse;
import com.datvm.hairbookingapp.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/combo")
public class ComboController {

    @Autowired
    ComboService comboService;

    @PostMapping
    public ApiResponse<ComboInfoResponse> createCombo(@RequestBody ComboCreationRequest request){
        return ApiResponse.<ComboInfoResponse>builder()
                .code(200)
                .result(comboService.createCombo(request))
                .build();
    }
}
