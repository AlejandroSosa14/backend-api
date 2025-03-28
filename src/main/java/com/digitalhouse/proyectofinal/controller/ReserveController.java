package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.entity.ReserveEntity;
import com.digitalhouse.proyectofinal.service.impl.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reserves")
public class ReserveController {

    private final ReserveService reserveService;

    @PostMapping
    public ReserveEntity save(@RequestBody ReserveEntity reserveEntity) {
        return reserveService.save(reserveEntity);
    }

    @GetMapping
    public List<ReserveEntity> allAdmin() {
        return reserveService.findAll();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("#id == @userRepository.findIdByName(authentication.name) or hasRole('ADMIN')")
    public List<ReserveEntity> findByUserId(@PathVariable Long id) {
        return reserveService.findByUserId(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == @userRepository.findIdByName(authentication.name) or hasRole('ADMIN')")
    public ReserveEntity findByIdAndUserId(@PathVariable Long id, @RequestParam Long userId){
        return reserveService.findByIdAndUserId(id, userId);
    }
}
