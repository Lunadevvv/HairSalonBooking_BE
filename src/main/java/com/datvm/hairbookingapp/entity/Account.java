package com.datvm.hairbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Email(message = "Invalid email")
    @Column(unique = true)
    String email;
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})\\b" , message = "Invalid phone number")
    @Column(unique = true)
    String phone;
    @Size(min = 6 , message = "Password must be exceed 6 characters ")
    String password;

}
