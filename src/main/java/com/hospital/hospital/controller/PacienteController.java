package com.hospital.hospital.controller;

import com.hospital.hospital.model.dto.PacienteDTO;
import com.hospital.hospital.model.entity.Direccion;
import com.hospital.hospital.model.entity.Paciente;
import com.hospital.hospital.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    @PostMapping("/registro")
public ResponseEntity<?> registrarPaciente(@RequestBody Map<String, Object> body) {
    try {
        @SuppressWarnings("unchecked")
        Map<String, String> dir = (Map<String, String>) body.get("direccion");

        Direccion direccion = new Direccion();
        direccion.setCalle(dir.get("calle"));
        direccion.setNumExt(dir.get("numExt"));
        direccion.setNumInt(dir.get("numInt"));
        direccion.setColonia(dir.get("colonia"));
        direccion.setCp(dir.get("cp"));
        direccion.setLocalidad(dir.get("localidad"));
        direccion.setEstado(dir.get("estado"));

        Paciente paciente = service.registrarPaciente(
                (String) body.get("correo"),
                (String) body.get("contrasena"),
                (String) body.get("nombre"),
                (String) body.get("apPaterno"),
                (String) body.get("apMaterno"),
                (String) body.get("nss"),
                (String) body.get("curp"),
                LocalDate.parse((String) body.get("fechaNacimiento")),
                Paciente.Sexo.valueOf((String) body.get("sexo")),
                (String) body.get("telefono"),
                (String) body.get("telefonoEmergencias"),
                Paciente.TipoSangre.fromValor((String) body.get("tipoSangre")),
                direccion
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(paciente);

    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
    }
}

    // Traer paciente por id_usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorIdUsuario(@PathVariable Integer idUsuario) {
        try {
            return ResponseEntity.ok(service.obtenerPorIdUsuario(idUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Actualizar paciente por id_usuario
    @PutMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> actualizarPorIdUsuario(
            @PathVariable Integer idUsuario,
            @RequestBody Paciente paciente) {
        try {
            return ResponseEntity.ok(service.actualizarPorIdUsuario(idUsuario, paciente));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // Traer todos los pacientes
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }
}  