package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid  @RequestBody UserEntity userEntity){
        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userEntity));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid  @RequestBody UserEntity userEntity){
        try {

            return ResponseEntity.ok().body(userService.update(id,userEntity));

        }catch (RuntimeException r){

            Map<String,String> error = new HashMap<>();
            error.put("error",r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok().body(userService.getById(id));
        } catch (RuntimeException r) {

            Map<String,String> error = new HashMap<>();
            error.put("error",r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {

        try {
            UserEntity userFound = userService.getById(id);
            userService.deleteById(userFound.getId());
            ResponseEntity.ok();
        } catch (RuntimeException r) {
            ResponseEntity.notFound();
        }

    }

}
