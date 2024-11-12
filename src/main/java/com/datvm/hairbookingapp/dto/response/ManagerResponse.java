package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.Salon;
import com.datvm.hairbookingapp.entity.Staff;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManagerResponse {
    private String id;
    private Staff staff;
    private Salon salon;
}
