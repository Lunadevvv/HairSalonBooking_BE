package com.datvm.hairbookingapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Salons")
public class Salons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salonId;
    private String salonName;
    private String salonAddress;
    private String phoneNumber;
    private String openingHours;
    private String description;
}
