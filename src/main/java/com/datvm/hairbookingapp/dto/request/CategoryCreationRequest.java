package com.datvm.hairbookingapp.dto.request;

import com.datvm.hairbookingapp.entity.Services;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CategoryCreationRequest {
    @NotNull(message = "categoryId can't be null")
    Long categoryId;
    @NotBlank(message = "categoryName can't be blank")
    String categoryName;
    @NotBlank(message = "categoryDescription can't be blank")
    String categoryDescription;
    List<Services> service;
}
