package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.service.CategoryService;
import com.digitalhouse.proyectofinal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid  @RequestBody CategoryEntity categoryEntity){
        return  ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(categoryEntity));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid  @RequestBody CategoryEntity categoryEntity){
        try {

            return ResponseEntity.ok().body(categoryService.update(id,categoryEntity));

        }catch (RuntimeException r){

            Map<String,String> error = new HashMap<>();
            error.put("error",r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok().body(categoryService.getById(id));
        } catch (RuntimeException r) {

            Map<String,String> error = new HashMap<>();
            error.put("error",r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {

        try {
            CategoryEntity userFound = categoryService.getById(id);
            categoryService.deleteById(userFound.getId());
            ResponseEntity.ok();
        } catch (RuntimeException r) {
            ResponseEntity.notFound();
        }

    }

}
