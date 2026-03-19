package com.hospital.hospital.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.hospital.hospital.model.repository.SignosVitalesRepository;
import com.hospital.hospital.model.entity.SignosVitales;

@Service
public class SignosVitalesService {

    // @Autowired
    private final SignosVitalesRepository signosVitalesRepository;
    public SignosVitalesService(SignosVitalesRepository signosVitalesRepository) {
        this.signosVitalesRepository = signosVitalesRepository;
    } //constructor para inyectar el repositorio
    
    public SignosVitales saveSignosVitales(SignosVitales signosVitales) {
        return signosVitalesRepository.save(signosVitales);
    } //guardar signos vitales
    
    public List<SignosVitales> getAllSignosVitales() {
        return signosVitalesRepository.findAll();
    } //obtener todos los signos vitales

    public SignosVitales getSignosVitalesById(Long id_signosVitales) {
        return signosVitalesRepository.findById(id_signosVitales).orElse(null);
    } //obtener signos vitales por id

    public List<SignosVitales> getSignosVitalesByCita(Long idCita) {
        return signosVitalesRepository.findByIdCita(idCita);
    } //obtener signos vitales por id de cita

    public void deleteSignosVitales(Long id_signosVitales) {
        signosVitalesRepository.deleteById(id_signosVitales);
    } //eliminar signos vitales por id

    public SignosVitales updateSignosVitales(Long id, SignosVitales actualizado) {
        return signosVitalesRepository.findById(id).map(existente -> {

            if (actualizado.getPesoKg() != null) {
                existente.setPesoKg(actualizado.getPesoKg());
            }

            if (actualizado.getTallaM() != null) {
                existente.setTallaM(actualizado.getTallaM());
            }

            if (actualizado.getPresionArterial() != null) {
                existente.setPresionArterial(actualizado.getPresionArterial());
            }

            if (actualizado.getFrecuenciaCardiaca() != null) {
                existente.setFrecuenciaCardiaca(actualizado.getFrecuenciaCardiaca());
            }

            if (actualizado.getFrecuenciaRespiratoria() != null) {
                existente.setFrecuenciaRespiratoria(actualizado.getFrecuenciaRespiratoria());
            }

            if (actualizado.getTemperatura() != null) {
                existente.setTemperatura(actualizado.getTemperatura());
            }

            if (actualizado.getSpo2() != null) {
                existente.setSpo2(actualizado.getSpo2());
            }

            if (actualizado.getGlucosa() != null) {
                existente.setGlucosa(actualizado.getGlucosa());
            }

            if (actualizado.getFechaRegistro() != null) {
                existente.setFechaRegistro(actualizado.getFechaRegistro());
            }

            if (actualizado.getIdCita() != null) {
                existente.setIdCita(actualizado.getIdCita());
            }

            return signosVitalesRepository.save(existente);

        }).orElse(null);
    }
}