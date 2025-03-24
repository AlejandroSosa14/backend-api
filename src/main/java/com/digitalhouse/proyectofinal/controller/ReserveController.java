package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.entity.ReserveEntity;
import com.digitalhouse.proyectofinal.service.impl.ReserveService;
import lombok.RequiredArgsConstructor;
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

}
