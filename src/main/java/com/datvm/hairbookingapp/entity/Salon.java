package com.datvm.hairbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Salon {
    @Id
    private String id;

    private boolean open;

    @NotNull
    private String address;
    @NotNull
    private String district;

    @OneToMany(mappedBy = "salons", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Staff> staff;

    @OneToOne(mappedBy = "salon",fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnore
    private Manager manager;

}
