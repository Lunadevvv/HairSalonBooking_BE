package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffResponse {
    private String code;

    private String firstName;

    private String lastName;

    private String gender;

    private int yob;

    private String phone;

    private String email;

    private LocalDate joinIn;

    private Role role;

    private String image;

    private boolean status;
}
