package com.digitalhouse.proyectofinal.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NonNull
    private String brand;

    @Setter
    @NonNull
    private String name;

    @Setter
    @NonNull
    private Integer model;

    @Setter
    @NonNull
    private Boolean status;

    @Setter
    @NonNull
    private String fuelType;

    @Setter
    @NonNull
    private String transmissionType;

    @Setter
    @NonNull
    private BigDecimal reserveCost;

    @Setter
    @Column(nullable = true, columnDefinition = "json")
    @JsonProperty("images")
    @JsonRawValue
    private String images;
}
