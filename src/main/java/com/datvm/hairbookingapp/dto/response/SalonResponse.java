package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalonResponse {

    private String id;

    private boolean open;

    private String address;

    private String district;
}
