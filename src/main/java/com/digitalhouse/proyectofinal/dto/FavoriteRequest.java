package com.digitalhouse.proyectofinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequest {
    private String username;

    @JsonProperty("id_car")
    private Long idCar;
}
