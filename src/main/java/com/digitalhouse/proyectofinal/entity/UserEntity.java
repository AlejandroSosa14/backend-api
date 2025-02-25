package com.digitalhouse.proyectofinal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank
    private String name;

    @Setter
    @NotBlank
    @Column(unique = true)
    private String email;

    @Setter
    @NotBlank
    private String password;

    @Setter
    @NotBlank
    private String type;

    @Setter
    @NotNull
    private Boolean active;

}
