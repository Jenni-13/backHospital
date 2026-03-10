package com.hospital.hospital.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hospital.hospital.model.entity.Diagnostico;
import com.hospital.hospital.service.DiagnosticoService;

@RestController
@RequestMapping("/diagnosticos")
@CrossOrigin(origins = "*")
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    // método solo se usará para ERRORES o mensajes específicos
    private ResponseEntity<Object> generarRespuesta(Object data, String mensaje, HttpStatus status) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", status.value());
        respuesta.put("mensaje", mensaje);
        respuesta.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        if (data != null) {
            respuesta.put("data", data);
        }
        return new ResponseEntity<>(respuesta, status);
    }

    @PostMapping
    public ResponseEntity<Object> createDiagnostico(@RequestBody Diagnostico diagnostico, @RequestParam Integer idCita) {

        Diagnostico d = diagnosticoService.saveDiagnostico(diagnostico, idCita);

        return generarRespuesta(d, "Diagnóstico creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAllDiagnosticos() {
        List<Diagnostico> lista = diagnosticoService.getAllDiagnosticos(); 
        return generarRespuesta(lista, "Lista de diagnósticos obtenida exitosamente", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDiagnosticoById(@PathVariable Long id) {
        Diagnostico d = diagnosticoService.getDiagnosticoById(id);
        if (d != null) { 
            return generarRespuesta(d, "Diagnóstico encontrado", HttpStatus.OK);
        } 
        return generarRespuesta(null,"El diagnóstico con ID " + id + " no existe", HttpStatus.NOT_FOUND);
    } 

    @GetMapping("/cita/{idCita}")
    public ResponseEntity<Object> getDiagnosticosByCita(@PathVariable Long idCita) { 
        List<Diagnostico> d = diagnosticoService.getDiagnosticosByCita(idCita);
        
        if (d != null && !d.isEmpty()) {
            return generarRespuesta(d, "Diagnosticos encontrados para la cita " + idCita, HttpStatus.OK);
        }
        
        return generarRespuesta(null, "La cita con ID " + idCita + " no tiene diagnosticos asociados", HttpStatus.NOT_FOUND);
    }       

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Long id, @RequestBody Diagnostico actualizado) {
        Diagnostico updated = diagnosticoService.updateDiagnostico(id, actualizado);
        if (updated != null) { 
            return generarRespuesta(updated, "Diagnóstico actualizado exitosamente", HttpStatus.OK);
        } 
        return generarRespuesta(null, "No se pudo actualizar: ID " + id + " no encontrado", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDiagnostico(@PathVariable Long id) {
        Diagnostico d = diagnosticoService.getDiagnosticoById(id);
        if (d != null) {
            diagnosticoService.deleteDiagnostico(id);
            return generarRespuesta(null, "Diagnóstico eliminado correctamente", HttpStatus.OK);
        } 
        return generarRespuesta(null, "Error al eliminar: ID " + id + " no existe", HttpStatus.NOT_FOUND);
    }
}