package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServicesResponse {
    String serviceId;
    String serviceName;
    String description;
    String duration;
    int price;
    String image;
    boolean status;
    Category categories;

}
