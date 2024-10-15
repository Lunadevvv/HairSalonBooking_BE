package com.datvm.hairbookingapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "combos")
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotNull(message = "Combo's price can't be null")
    private int price;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "combo_services",
            joinColumns = @JoinColumn(name = "combo_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Services> services;
}
