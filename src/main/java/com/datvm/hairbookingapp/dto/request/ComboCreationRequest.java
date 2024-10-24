package com.datvm.hairbookingapp.dto.request;

import com.datvm.hairbookingapp.entity.Services;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboCreationRequest {
    @NotNull(message = "Combo's name can't be null")
    private String name;

//    @NotNull(message = "Combo's price can't be null")
//    private int price;

    private String description;

    private List<String> listServiceId;
}
