package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.Staff;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalonResponse {

    private String id;

    private boolean open;

    private String address;

    private String district;
}
