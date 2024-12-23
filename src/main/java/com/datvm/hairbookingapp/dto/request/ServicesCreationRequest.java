package com.datvm.hairbookingapp.dto.request;

import com.datvm.hairbookingapp.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServicesCreationRequest {
    @NotBlank(message = "categoryId can't be blank")
    String categoryId;

    @NotBlank(message = "service name can't be blank")
    String serviceName;

    @NotBlank(message = "description can't be blank")
    String description;

    @NotBlank(message = "duration can't be blank")
    String duration;

    @NotNull(message = "price can't be null")
    int price;

    @NotBlank(message = "image can't be blank. Please add image url")
    String image;
}
