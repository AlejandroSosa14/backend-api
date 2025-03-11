package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.service.ICarService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Collections;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.digitalhouse.proyectofinal.entity.CarEntity;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
@Tag(name = "Car Controller", description = "API para la gestión de automóviles")
@Validated
public class CarController {

    private final ICarService carService;
    private static final String PATH_UPLOAD = "cars/";

    @GetMapping
    @Operation(summary = "Obtener todos los autos", description = "Devuelve una lista de todos los autos registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de autos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarEntity.class)))
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        Page<CarEntity> carsPage = carService.getAll(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("content", carsPage.getContent());
        response.put("totalPages", carsPage.getTotalPages());
        response.put("totalElements", carsPage.getTotalElements());
        response.put("currentPage", page);

        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/transmission/{transmission}")
    @Operation(summary = "Buscar autos por transmisión", description = "Filtra autos por tipo de transmisión (Manual o Automático).")
    @ApiResponse(responseCode = "200", description = "Lista de autos filtrada exitosamente", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"id\": 1, \"brand\": \"Toyota\", \"model\": \"Corolla\", \"transmission\": \"Automatic\"}]")))
    @ApiResponse(responseCode = "404", description = "No se encontraron autos con esa transmisión")
    public ResponseEntity<?> findByTransmission(
            @Parameter(description = "Tipo de transmisión a buscar", example = "Automatic") @PathVariable String transmission,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CarEntity> cars = carService.findByTransmission(transmission, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", cars.getContent());
        response.put("totalPages", cars.getTotalPages());
        response.put("totalElements", cars.getTotalElements());
        response.put("currentPage", page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar auto por ID", description = "Devuelve los detalles de un automóvil específico según su ID.")
    @ApiResponse(responseCode = "200", description = "Auto encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarEntity.class)))
    @ApiResponse(responseCode = "404", description = "Auto no encontrado")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID del auto a buscar", example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(carService.getById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Registrar un auto", description = "Guarda un nuevo auto en el sistema.")
    @ApiResponse(responseCode = "201", description = "Auto creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarEntity.class)))
    @ApiResponse(responseCode = "400", description = "Error en la creación del auto")
    public ResponseEntity<?> create(@RequestPart("car") @Valid @JsonProperty("car") String carJson, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        CarEntity carEntity = objectMapper.readValue(carJson, CarEntity.class);
        CarEntity carEntitySave = carService.create(carEntity, PATH_UPLOAD, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(carEntitySave);

    }

//    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "Actualiza un auto", description = "Actualiza auto en el sistema.")
//    @ApiResponse(responseCode = "200", description = "Auto actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarEntity.class)))
//    @ApiResponse(responseCode = "400", description = "Error en la actualización del auto")
//    public ResponseEntity<?> update(
//            @PathVariable(name = "id") Long id,
//            @RequestParam("car") String carJson,  // Cambio aquí
//            @RequestPart(value = "files", required = false) List<MultipartFile> files
//    ) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//
//            // Convertimos el JSON de String a un objeto Java
//            CarEntity carEntity = objectMapper.readValue(carJson, CarEntity.class);
//
//            // Llamamos al servicio para actualizar el auto
//            CarEntity carEntityUpdate = carService.update(id, carEntity, files);
//            return ResponseEntity.status(HttpStatus.OK).body(carEntityUpdate);
//
//        } catch (JsonProcessingException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar el JSON del auto: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al actualizar el auto: " + e.getMessage());
//        }
//    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable(name = "id") Long id,
            @RequestParam("car") String carJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestParam(value = "removedImages", required = false) String removedImagesJson
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            CarEntity carEntity = objectMapper.readValue(carJson, CarEntity.class);

            // Convertir las imágenes eliminadas de JSON a lista
            List<String> removedImages = removedImagesJson != null && !removedImagesJson.isEmpty()
                    ? objectMapper.readValue(removedImagesJson, List.class)
                    : Collections.emptyList();

            CarEntity carEntityUpdate = carService.update(id, carEntity, PATH_UPLOAD, files, removedImages);
            return ResponseEntity.status(HttpStatus.OK).body(carEntityUpdate);

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar el JSON del auto: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al actualizar el auto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un auto", description = "Elimina un auto del sistema según su ID.")
    @ApiResponse(responseCode = "200", description = "Auto eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Auto no encontrado")
    public ResponseEntity<Void> deleteById(@Parameter(description = "ID del auto a eliminar", example = "1") @PathVariable Long id) {

        carService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCars(@RequestParam("query") String query, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id,asc") String sort) {

        String[] sortParams = sort.split(",");
        Sort sortRequest = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        Pageable pageable = PageRequest.of(page, size, sortRequest);
        Page<CarEntity> cars = carService.searchCars(query, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", cars.getContent());
        response.put("totalPages", cars.getTotalPages());
        response.put("totalElements", cars.getTotalElements());
        response.put("currentPage", cars.getNumber());

        return ResponseEntity.ok(response);
    }

}
