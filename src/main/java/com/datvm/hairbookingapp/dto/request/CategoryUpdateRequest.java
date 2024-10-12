package com.datvm.hairbookingapp.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CategoryUpdateRequest {
    @NotBlank(message = "categoryName can't be blank")
    String categoryName;
    @NotBlank(message = "categoryDescription can't be blank")
    String categoryDescription;
}
