package com.datvm.hairbookingapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    private String serviceName;

    private String description;

    private String duration;
    private BigDecimal price;

    @ManyToOne(targetEntity = Category.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category categories;
}
