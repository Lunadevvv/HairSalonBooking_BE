package com.datvm.hairbookingapp.dto.request;

import com.datvm.hairbookingapp.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;
}
