package com.datvm.hairbookingapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "salon")
@AllArgsConstructor
@NoArgsConstructor
public class Salon {
    @Id
    private Long salonId;

    private String salonName;

    private String salonAddress;

    private String phoneNumber;

    private String openingHours;

    private String description;
}
