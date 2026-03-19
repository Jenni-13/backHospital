package com.hospital.hospital.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.hospital.hospital.model.repository.DiagnosticoRepository;
import com.hospital.hospital.model.entity.Diagnostico;
import com.hospital.hospital.model.entity.Cita;
import com.hospital.hospital.model.repository.CitaRepository;

@Service
public class DiagnosticoService {

    // @Autowired
    private final DiagnosticoRepository diagnosticoRepository;
    private CitaRepository citaRepository;
    
    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository,
                              CitaRepository citaRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.citaRepository = citaRepository;
    }//constructor para inyectar el repositorio
    
    public Diagnostico saveDiagnostico(Diagnostico diagnostico, Integer idCita) {
        Cita cita = citaRepository.findById(idCita).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        diagnostico.setCita(cita);
        return diagnosticoRepository.save(diagnostico);
    } //guardar diagnostico
    
    public List<Diagnostico> getAllDiagnosticos() {
        return diagnosticoRepository.findAll();
    } //obtener todos los diagnosticos

    public Diagnostico getDiagnosticoById(Long id_diagnostico) {
        return diagnosticoRepository.findById(id_diagnostico).orElse(null);
    } //obtener diagnostico por id

    public List<Diagnostico> getDiagnosticosByCita(Long idCita) {
        return diagnosticoRepository.findByCita_IdCita(idCita);
    } //obtener diagnosticos por id de cita

    public void deleteDiagnostico(Long id_diagnostico) {
        diagnosticoRepository.deleteById(id_diagnostico);
    } //eliminar diagnostico por id

    public Diagnostico updateDiagnostico(Long id, Diagnostico actualizado) {
        return diagnosticoRepository.findById(id).map(existente -> {

            if (actualizado.getCie10() != null) {
                existente.setCie10(actualizado.getCie10());
            }

            if (actualizado.getDescripcion() != null) {
                existente.setDescripcion(actualizado.getDescripcion());
            }

            if (actualizado.getTipo() != null) {
                existente.setTipo(actualizado.getTipo());
            }

            if (actualizado.getMedicamentos_base() != null) {
                existente.setMedicamentos_base(actualizado.getMedicamentos_base());
            }

            if (actualizado.getTratamiento() != null) {
                existente.setTratamiento(actualizado.getTratamiento());
            }

            if (actualizado.getIndicaciones() != null) {
                existente.setIndicaciones(actualizado.getIndicaciones());
            }

            if (actualizado.getFun_alta() != null) {
                existente.setFun_alta(actualizado.getFun_alta());
            }

            if (actualizado.getCita() != null) {
                existente.setCita(actualizado.getCita());
            }

            return diagnosticoRepository.save(existente);

        }).orElse(null);
    }
}