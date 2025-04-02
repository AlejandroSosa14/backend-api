package com.digitalhouse.proyectofinal.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @ManyToOne
    @Setter
    @NotNull(message = "Category required")
    private CategoryEntity category;
    @JsonIgnore
    public List<String> getImagesList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(images, new TypeReference<List<String>>() {});
    }

    @Setter
    @JsonProperty("postDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate postDate;

    @OneToMany(mappedBy = "id")
    private List<ReserveEntity> reserves;

    @Setter
    @NotBlank(message = "Location city required")
    private String locationCity;

    @Setter
    @NotBlank(message = "Location country required")
    private String locationCountry;

    @Setter
    @NotBlank(message = "Color required")
    private String color;

    @Setter
    @ElementCollection
    private Map<Integer, String> scores = new HashMap<>();
}
