package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import com.digitalhouse.proyectofinal.service.ICategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
@Tag(name = "Category Controller", description = "API para la gestión de categorías")
public class CategoryController {

    private final ICategoryService categoryService;
    private static final String PATH_UPLOAD = "categories/";

    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Devuelve una lista de todas las categorías registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class)))
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva categoría", description = "Registra una nueva categoría en el sistema.")
    @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class)))
    @ApiResponse(responseCode = "400", description = "Error en la creación de la categoría")
    public ResponseEntity<?> create(
            @Valid @RequestPart("category") String categoryJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CategoryEntity categoryEntity = objectMapper.readValue(categoryJson, CategoryEntity.class);

            // Llamamos al service con la imagen
            CategoryEntity createdCategory = categoryService.create(categoryEntity, PATH_UPLOAD, file);

            // 🔍 Imprimir resultado para verificar que la imagen se guardó correctamente
            System.out.println("Categoría creada: " + createdCategory.getName() + " - Imagen: " + createdCategory.getImage());

            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar la categoría: " + e.getMessage());
        }
    }


    @PutMapping("{id}")
    @Operation(summary = "Actualizar una categoría", description = "Actualiza la información de una categoría existente.")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class)))
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestPart("category") String categoryJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CategoryEntity categoryEntity = objectMapper.readValue(categoryJson, CategoryEntity.class);
            return ResponseEntity.ok(categoryService.update(id, categoryEntity, PATH_UPLOAD, file));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar la categoría: " + e.getMessage());
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Obtener una categoría por ID", description = "Devuelve los detalles de una categoría específica.")
    @ApiResponse(responseCode = "200", description = "Categoría encontrada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class)))
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    public ResponseEntity<?> getById(@Parameter(description = "ID de la categoría a buscar", example = "1") @PathVariable Long id) {

        try {
            return ResponseEntity.ok().body(categoryService.getById(id));
        } catch (RuntimeException r) {

            Map<String,String> error = new HashMap<>();
            error.put("error",r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría del sistema según su ID.")
    @ApiResponse(responseCode = "200", description = "Categoría eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    public void deleteById(@Parameter(description = "ID de la categoría a eliminar", example = "1") @PathVariable Long id) {
            categoryService.deleteById(id);
    }

}
