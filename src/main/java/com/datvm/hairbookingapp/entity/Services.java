package com.datvm.hairbookingapp.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "Services")
public class Services {
    @Id
    private String serviceId;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String image;

    private boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoryId")
    private Category categories;

    @ManyToMany(mappedBy = "services")
    @JsonIgnore
    private List<Combo> combos;

    @ManyToMany(mappedBy = "services") // Liên kết với thuộc tính services trong Booking
    @JsonIgnore
    private List<Booking> bookings; // Danh sách booking liên quan đến service
}
