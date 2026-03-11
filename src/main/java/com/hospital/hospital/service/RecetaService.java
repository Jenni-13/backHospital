package com.hospital.hospital.service;

import com.hospital.hospital.model.dto.RecetaDTO;
import com.hospital.hospital.model.dto.RecetaRequestDTO;
import com.hospital.hospital.model.entity.Cita;
import com.hospital.hospital.model.entity.Medicamento;
import com.hospital.hospital.model.entity.Receta;
import com.hospital.hospital.model.repository.CitaRepository;
import com.hospital.hospital.model.repository.MedicamentoRepository;
import com.hospital.hospital.model.repository.RecetaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository recetaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final CitaRepository citaRepository;

    public List<RecetaDTO> obtenerRecetasPorPaciente(Integer idUsuario) {
        List<Receta> recetas = recetaRepository.findByUsuarioId(idUsuario);

        return recetas.stream().map(receta -> {
            RecetaDTO dto = new RecetaDTO();
            dto.setIdReceta(receta.getIdReceta());
            dto.setFolio(receta.getFolio());
            dto.setFecha(receta.getFecha());
            dto.setVencimiento(receta.getVencimiento());
            dto.setEstado(receta.getEstado().name());

            List<RecetaDTO.MedicamentoDTO> medicamentos = medicamentoRepository
                    .findByRecetaIdReceta(receta.getIdReceta())
                    .stream().map(m -> {
                        RecetaDTO.MedicamentoDTO medDTO = new RecetaDTO.MedicamentoDTO();
                        medDTO.setNombre(m.getNombre());
                        medDTO.setPresentacion(m.getPresentacion());
                        medDTO.setDosis(m.getDosis());
                        medDTO.setFrecuencia(m.getFrecuencia());
                        medDTO.setDuracion(m.getDuracion());
                        medDTO.setCantidad(m.getCantidad());
                        medDTO.setVia(m.getVia().name());
                        return medDTO;
                    }).toList();

            dto.setMedicamentos(medicamentos);
            return dto;
        }).toList();
    }

    @Transactional
    public RecetaDTO crearReceta(RecetaRequestDTO request) {

        // 1. Validar que la cita exista
        Cita cita = citaRepository.findById(request.getIdCita())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + request.getIdCita()));

        // 2. Validar folio único
        if (recetaRepository.existsByFolio(request.getFolio())) {
            throw new RuntimeException("Ya existe una receta con el folio: " + request.getFolio());
        }

        // 3. Construir y guardar la Receta
        Receta receta = new Receta();
        receta.setFolio(request.getFolio());
        receta.setFecha(request.getFecha());
        receta.setVencimiento(request.getVencimiento());
        receta.setEstado(Receta.EstadoReceta.activa);
        receta.setCita(cita);

        Receta recetaGuardada = recetaRepository.save(receta);

        // 4. Guardar cada medicamento asociado
        List<Medicamento> medicamentos = request.getMedicamentos().stream().map(m -> {
            Medicamento med = new Medicamento();
            med.setNombre(m.getNombre());
            med.setPresentacion(m.getPresentacion());
            med.setDosis(m.getDosis());
            med.setFrecuencia(m.getFrecuencia());
            med.setDuracion(m.getDuracion());
            med.setCantidad(m.getCantidad());
            med.setVia(Medicamento.ViaAdministracion.valueOf(m.getVia()));
            med.setReceta(recetaGuardada);
            return med;
        }).toList();

        medicamentoRepository.saveAll(medicamentos);

        // 5. Reutilizar lógica existente para construir el DTO de respuesta
        RecetaDTO dto = new RecetaDTO();
        dto.setIdReceta(recetaGuardada.getIdReceta());
        dto.setFolio(recetaGuardada.getFolio());
        dto.setFecha(recetaGuardada.getFecha());
        dto.setVencimiento(recetaGuardada.getVencimiento());
        dto.setEstado(recetaGuardada.getEstado().name());
        dto.setMedicamentos(medicamentos.stream().map(m -> {
            RecetaDTO.MedicamentoDTO medDTO = new RecetaDTO.MedicamentoDTO();
            medDTO.setNombre(m.getNombre());
            medDTO.setPresentacion(m.getPresentacion());
            medDTO.setDosis(m.getDosis());
            medDTO.setFrecuencia(m.getFrecuencia());
            medDTO.setDuracion(m.getDuracion());
            medDTO.setCantidad(m.getCantidad());
            medDTO.setVia(m.getVia().name());
            return medDTO;
        }).toList());

        return dto;
    }
}