package com.datvm.hairbookingapp.entity;
import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Services")
public class Services {
    @Id
    private Long serviceId;
    @Column(nullable = false)
    private String serviceName;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String duration;
    @Column(nullable = false)
    private BigDecimal price;

    private String image;
    @ManyToOne(targetEntity = Category.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category categories;
}
