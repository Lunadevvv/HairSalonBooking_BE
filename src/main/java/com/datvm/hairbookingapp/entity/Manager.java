package com.datvm.hairbookingapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Manager {
    @Id
    private String id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staffId")
    private Staff staff;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salonId")
    private Salon salon;
}
