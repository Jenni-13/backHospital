package com.hospital.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.hospital.model.entity.Expediente;
import com.hospital.hospital.model.entity.Paciente;
import com.hospital.hospital.model.repository.ExpedienteRepository;
import com.hospital.hospital.model.repository.PacienteRepository;
import com.hospital.hospital.util.JwtUtil;

@Service
public class ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final PacienteRepository pacienteRepository;

    public ExpedienteService(ExpedienteRepository expedienteRepository,
                             PacienteRepository pacienteRepository) {
        this.expedienteRepository = expedienteRepository;
        this.pacienteRepository = pacienteRepository;
    }

    // ✅ Guardar expediente
    public Expediente saveExpediente(Expediente expediente) {

        Long idUsuario = JwtUtil.getIdUsuario().longValue();

        // Buscar paciente por id_usuario
        Paciente paciente = pacienteRepository
                .findByUsuario_IdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        expediente.setPaciente(paciente);

        return expedienteRepository.save(expediente);
    }

    public List<Expediente> getAllExpedientes() {
        return expedienteRepository.findAll();
    }

    public Expediente getExpedienteById(Long id) {
        return expedienteRepository.findById(id).orElse(null);
    }

    public void deleteExpediente(Long id) {
        expedienteRepository.deleteById(id);
    }

    // ✅ Update SIN tocar paciente (seguridad)
    public Expediente updateExpediente(Long id, Expediente actualizado) {

        return expedienteRepository.findById(id).map(existente -> {

            if (actualizado.getFolio() != null)
                existente.setFolio(actualizado.getFolio());

            if (actualizado.getAnt_heredofamiliares() != null)
                existente.setAnt_heredofamiliares(actualizado.getAnt_heredofamiliares());

            if (actualizado.getAnt_patologicos() != null)
                existente.setAnt_patologicos(actualizado.getAnt_patologicos());

            if (actualizado.getAnt_quirurgicos() != null)
                existente.setAnt_quirurgicos(actualizado.getAnt_quirurgicos());

            if (actualizado.getAnt_alergicos() != null)
                existente.setAnt_alergicos(actualizado.getAnt_alergicos());

            if (actualizado.getAnt_cronicas() != null)
                existente.setAnt_cronicas(actualizado.getAnt_cronicas());

            if (actualizado.getAnt_ginecoobstetricos() != null)
                existente.setAnt_ginecoobstetricos(actualizado.getAnt_ginecoobstetricos());

            if (actualizado.getObservaciones() != null)
                existente.setObservaciones(actualizado.getObservaciones());

            // 🚨 NO actualizar paciente aquí por seguridad

            return expedienteRepository.save(existente);

        }).orElse(null);
    }
}