package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FeedbackCreateRequest {
    @NotBlank(message = "feedbackId can't be blank")
    private String id;
    private String feedback;
    @Pattern(regexp = "^[1-5]$" , message = "Rate must be between 1-5")
    private String rate;
}
