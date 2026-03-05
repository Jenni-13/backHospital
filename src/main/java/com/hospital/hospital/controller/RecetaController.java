package com.hospital.hospital.controller;

import com.hospital.hospital.model.dto.RecetaDTO;
import com.hospital.hospital.model.dto.RecetaRequestDTO;
import com.hospital.hospital.service.RecetaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/receta")
@RequiredArgsConstructor
public class RecetaController {

    private final RecetaService recetaService;

    // Ver todas las recetas del paciente con sus medicamentos
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<?> obtenerRecetas(@PathVariable Integer idPaciente) {
        try {
            List<RecetaDTO> recetas = recetaService.obtenerRecetasPorPaciente(idPaciente);
            return ResponseEntity.ok(recetas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Agrega este endpoint al controlador existente
    @PostMapping
    public ResponseEntity<?> crearReceta(@RequestBody @Valid RecetaRequestDTO request) {
    try {
        RecetaDTO receta = recetaService.crearReceta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(receta);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", "Valor de enum inválido: " + e.getMessage()));
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }
}
}
