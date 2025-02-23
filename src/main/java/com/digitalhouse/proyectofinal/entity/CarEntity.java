package com.digitalhouse.proyectofinal.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 17,nullable = false,  unique = true)
    @NotBlank(message = "Serial number required")
    @Size(min = 17, max = 17,message = "Serial number must be 17 characters")
    private String serialNumber;

    @Setter
    @NotBlank(message = "Brand required")
    private String brand;

    @Setter
    @NotBlank(message = "Name required")
    private String name;

    @Setter
    @NotNull(message = "Model required")
    @Min(value = 1960, message = "Model must be grater than 1960")
    @Max(value = 2025, message = "Model must be less than 2025")
    private Integer model;

    @Setter
    @NotNull(message = "Model required")
    private Boolean status;

    @Setter
    @NotBlank(message = "Fuel type required")
    private String fuelType;

    @Setter
    @NotBlank(message = "Transmission type required")
    private String transmissionType;

    @Setter
    @NotNull(message = "Model required")
    @Positive(message = "The cost must be positive")
    @Digits(integer = 4, fraction = 2, message = "Format error for reserve cost")
    @DecimalMin(value = "0.0", inclusive = false, message = "The cost must be greater than 0.0")
    private BigDecimal reserveCost;

    @Setter
    @Column(columnDefinition = "json")
    @JsonProperty("images")
    @JsonRawValue
    private String images;

    @OneToOne
    @Setter
    private Category category;
}
