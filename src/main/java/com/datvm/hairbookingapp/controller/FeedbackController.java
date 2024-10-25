package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.FeedbackCreateRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.FeedbackResponse;
import com.datvm.hairbookingapp.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @PostMapping
    public ApiResponse<FeedbackResponse> submitFeedback(@RequestBody @Valid FeedbackCreateRequest feedbackCreateRequest) {
        return ApiResponse.<FeedbackResponse>builder()
                .result(feedbackService.submitFeedback(feedbackCreateRequest))
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<FeedbackResponse> getFeedback(@PathVariable String id) {
        return ApiResponse.<FeedbackResponse>builder()
                .result(feedbackService.getFeedbackById(id))
                .build();
    }
    @GetMapping
    public ApiResponse<List<FeedbackResponse>> getFeedbacks() {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .result(feedbackService.getAllFeedbacks())
                .build();
    }
}
