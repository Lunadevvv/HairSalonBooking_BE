package com.datvm.hairbookingapp.dto.response;


import com.datvm.hairbookingapp.entity.Services;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CategoryResponse {
    String categoryId;
    String categoryName;
    String categoryDescription;
}
