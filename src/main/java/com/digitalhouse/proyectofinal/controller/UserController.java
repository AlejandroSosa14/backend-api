package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.dto.FavoriteRequest;
import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista con todos los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class)))
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema.")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class)))
    @ApiResponse(responseCode = "400", description = "Error en la creación del usuario")
    public ResponseEntity<?> create(@Valid @RequestBody UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userEntity));
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar un usuario", description = "Actualiza la información de un usuario existente.")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<?> update(@Parameter(description = "ID del usuario a actualizar", example = "1") @PathVariable Long id,
                                    @Valid @RequestBody UserEntity userEntity) {
        try {

            return ResponseEntity.ok().body(userService.update(id, userEntity));

        } catch (RuntimeException r) {

            Map<String, String> error = new HashMap<>();
            error.put("error", r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Obtener un usuario por ID", description = "Devuelve los detalles de un usuario específico.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<?> getById(@Parameter(description = "ID del usuario a buscar", example = "1") @PathVariable Long id) {

        try {
            return ResponseEntity.ok().body(userService.getById(id));
        } catch (RuntimeException r) {

            Map<String, String> error = new HashMap<>();
            error.put("error", r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema según su ID.")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public void deleteById(@Parameter(description = "ID del usuario a eliminar", example = "1") @PathVariable Long id) {

        UserEntity userFound = userService.getById(id);
        userService.deleteById(userFound.getId());

    }

    @PostMapping("/favorites")
    public void setFavorites(@RequestBody FavoriteRequest favoriteRequest) {
        userService.setFavorites(favoriteRequest);
    }

    @GetMapping("/favorites/{username}")
    public ResponseEntity<Set<CarEntity>> getFavorites(@PathVariable String username) {
        {
            return ResponseEntity.ok(userService.getFavorites(username));
        }
    }

    @PutMapping("/favorites/{username}/{id}")
    public void updateFavorites(@PathVariable String username, @PathVariable Long id) {
        userService.updateFavorites(username, id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserEntity> findByUsername(@PathVariable String name) {
        {
            return ResponseEntity.ok(userService.findByName(name));
        }
    }
}