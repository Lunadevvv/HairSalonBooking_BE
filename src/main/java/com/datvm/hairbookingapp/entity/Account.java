package com.datvm.hairbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "First name can't be blank")
    @Size(min = 2, message = "First name must be at least 2 characters")
    private String firstName;

    @NotBlank(message = "Last name can't be blank")
    @Size(min = 2, message = "Last name must be at least 2 characters")
    private String lastName;

    @Email(message = "Invalid email")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})\\b" , message = "Invalid phone number")
    @Column(unique = true)
    private String phone;

    @Size(min = 6 , message = "Password must be exceed 6 characters ")
    private String password;

    @Enumerated(EnumType.STRING) //Luu xuong database se theo kieu string
    private Role role;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Staff staff;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(this.role != null) authorities.add(new SimpleGrantedAuthority(this.role.toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
