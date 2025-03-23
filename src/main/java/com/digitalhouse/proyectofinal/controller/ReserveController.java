package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.entity.ReserveEntity;
import com.digitalhouse.proyectofinal.service.impl.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reserves")
public class ReserveController {

    private final ReserveService reserveService;

    @PostMapping
    public ReserveEntity save(@RequestBody ReserveEntity reserveEntity) {
        return reserveService.save(reserveEntity);
    }

}
