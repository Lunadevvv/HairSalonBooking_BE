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
    @Column(nullable = false)
    private String salonName;
    @Column(nullable = false)
    private String salonAddress;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String openingHours;
    @Column(nullable = false)
    private String description;
}
