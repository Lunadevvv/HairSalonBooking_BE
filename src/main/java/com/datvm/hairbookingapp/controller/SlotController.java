package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.SlotCreationRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.entity.Slot;
import com.datvm.hairbookingapp.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/slot")
public class SlotController {
    @Autowired
    SlotService slotService;

    @PostMapping
    public ApiResponse createSlot(@RequestBody SlotCreationRequest request){
        slotService.createSlot(request);
        return ApiResponse.builder()
                .code(201)
                .message("Tạo mới slot thành công!")
                .build();
    }

    @GetMapping
    public ApiResponse<List<Slot>> getAllSlot(){
        return ApiResponse.<List<Slot>>builder()
                .code(200)
                .result(slotService.getAllSlot())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse updateSlotById(@PathVariable Long id, @RequestBody SlotCreationRequest request){
        slotService.updateSlotById(id, request);
        return ApiResponse.builder()
                .code(201)
                .message("Update slot " + id + " thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteSlotById(@PathVariable Long id){
        slotService.deleteSlotById(id);
        return ApiResponse.builder()
                .code(201)
                .message("Xóa slot " + id + " thành công!")
                .build();
    }
}
