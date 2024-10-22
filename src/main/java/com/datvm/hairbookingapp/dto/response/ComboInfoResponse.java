package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.Services;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComboInfoResponse {
    private Long id;

    private String name;

    private int price;

    private String description;

    private List<Services> listServices;
}
