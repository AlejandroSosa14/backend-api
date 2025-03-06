package com.digitalhouse.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Setter
    @NotBlank(message = "Name required")
    @Column(unique = true, nullable = false)
    private String name;

//    @Setter
//    private String image;

    @Setter
    @Column(nullable = true, length = 500)
    private String description;

}
