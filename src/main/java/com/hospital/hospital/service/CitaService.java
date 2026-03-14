package com.hospital.hospital.service;

import com.hospital.hospital.model.dto.CitaDTO;
import com.hospital.hospital.model.dto.CompletarCitaRequest;
import com.hospital.hospital.model.entity.Cita;
import com.hospital.hospital.model.entity.Diagnostico;
import com.hospital.hospital.model.entity.Medicamento;
import com.hospital.hospital.model.entity.Medico;
import com.hospital.hospital.model.entity.Paciente;
import com.hospital.hospital.model.entity.Receta;
import com.hospital.hospital.model.entity.SignosVitales;
import com.hospital.hospital.model.repository.CitaRepository;
import com.hospital.hospital.model.repository.DiagnosticoRepository;
import com.hospital.hospital.model.repository.MedicamentoRepository;
import com.hospital.hospital.model.repository.MedicoRepository;
import com.hospital.hospital.model.repository.PacienteRepository;
import com.hospital.hospital.model.repository.RecetaRepository;
import com.hospital.hospital.model.repository.SignosVitalesRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final SignosVitalesRepository signosVitalesRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final RecetaRepository recetaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public CitaService(CitaRepository citaRepository,
                       SignosVitalesRepository signosVitalesRepository,
                       DiagnosticoRepository diagnosticoRepository,
                       RecetaRepository recetaRepository,
                       MedicamentoRepository medicamentoRepository,
                        MedicoRepository medicoRepository,
                        PacienteRepository pacienteRepository) {
        this.citaRepository = citaRepository;
        this.signosVitalesRepository = signosVitalesRepository;
        this.diagnosticoRepository = diagnosticoRepository;
        this.recetaRepository = recetaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    // Determina el turno según la hora
    private Medico.Turno determinarTurno(LocalTime hora) {
        if (hora.isBefore(LocalTime.of(14, 0))) {
            return Medico.Turno.matutino;
        } else if (hora.isBefore(LocalTime.of(20, 0))) {
            return Medico.Turno.vespertino;
        } else {
            return Medico.Turno.nocturno;
        }
    }

    // Asigna el médico con menos citas en esa fecha y turno
    private Medico asignarMedico(LocalDate fecha, LocalTime hora) {
        Medico.Turno turno = determinarTurno(hora);
        List<Medico> medicos = citaRepository.findMedicosByTurno(turno);

        if (medicos.isEmpty()) {
            throw new RuntimeException("No hay médicos disponibles en ese horario");
        }

        return medicos.stream()
                .min((m1, m2) -> (int) (citaRepository.countByMedicoAndFecha(m1, fecha)
                        - citaRepository.countByMedicoAndFecha(m2, fecha)))
                .orElseThrow(() -> new RuntimeException("No se pudo asignar un médico"));
    }

    @Transactional
    public Cita agendarCita(Integer idPaciente, LocalDate fecha, LocalTime hora,
            String motivo, Cita.TipoCita tipo) {

        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Medico medico = asignarMedico(fecha, hora);

        Cita cita = new Cita();
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setMotivo(motivo);
        cita.setTipo(tipo);
        cita.setEstado(Cita.EstadoCita.pendiente);
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFolio(generarFolio());

        return citaRepository.save(cita);
    }

    // Ver citas del paciente
    public List<Cita> obtenerCitasPorPaciente(Integer idPaciente) {
        return citaRepository.findByPacienteIdPaciente(idPaciente);
    }

    // Cancelar cita
    @Transactional
    public Cita cancelarCita(Integer idCita) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (cita.getEstado() == Cita.EstadoCita.completada) {
            throw new RuntimeException("No se puede cancelar una cita completada");
        }

        cita.setEstado(Cita.EstadoCita.cancelada);
        return citaRepository.save(cita);
    }

    private String generarFolio() {
        return "CITA-" + System.currentTimeMillis();
    }


    // Obtener todas las citas del médico autenticado
    public List<CitaDTO> getCitasByMedico(Integer idUsuario) {
        Medico medico = medicoRepository.findByUsuarioIdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        return citaRepository.findByMedicoIdMedico(medico.getIdMedico())
                .stream()
                .map(CitaDTO::new)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
public void completarCita(Integer idCita, CompletarCitaRequest request) {

    // 1. Buscar y validar la cita
    Cita cita = citaRepository.findById(idCita)
            .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + idCita));

    // 2. Signos Vitales
    CompletarCitaRequest.SignosVitalesDTO svDTO = request.getSignosVitales();
    if (svDTO != null) {
        SignosVitales sv = new SignosVitales();
        sv.setPesoKg(svDTO.getPesoKg());
        sv.setTallaM(svDTO.getTallaM());
        sv.setPresionArterial(svDTO.getPresionArterial());
        sv.setFrecuenciaCardiaca(svDTO.getFrecuenciaCardiaca());
        sv.setFrecuenciaRespiratoria(svDTO.getFrecuenciaRespiratoria());
        sv.setTemperatura(svDTO.getTemperatura());
        sv.setSpo2(svDTO.getSpo2());
        sv.setGlucosa(svDTO.getGlucosa());
        sv.setIdCita(idCita.longValue());
        signosVitalesRepository.save(sv);
    }

    // 3. Diagnóstico ← CORREGIDO: setIdCita en lugar de setCita
    // 3. Diagnóstico
CompletarCitaRequest.DiagnosticoDTO dxDTO = request.getDiagnostico();
if (dxDTO != null) {
    Diagnostico dx = new Diagnostico();
    dx.setCie10(dxDTO.getCie10());
    dx.setDescripcion(dxDTO.getDescripcion());
    dx.setTipo(dxDTO.getTipo());
    dx.setMedicamentos_base(dxDTO.getMedicamentosBase());
    dx.setTratamiento(dxDTO.getTratamiento());
    dx.setIndicaciones(dxDTO.getIndicaciones());
    dx.setFun_alta(dxDTO.getFunAlta());
    dx.setIdCita(idCita.longValue()); // ← ¿tienes esta línea exactamente así?
    diagnosticoRepository.save(dx);
}

    // 4. Receta + Medicamentos
    CompletarCitaRequest.RecetaDTO recetaDTO = request.getReceta();
    if (recetaDTO != null) {
        Receta receta = new Receta();
        receta.setFolio(recetaDTO.getFolio());
        receta.setFecha(LocalDate.now());
        receta.setVencimiento(recetaDTO.getVencimiento());
        receta.setEstado(Receta.EstadoReceta.activa);
        receta.setCita(cita);
        Receta recetaGuardada = recetaRepository.save(receta);

        if (recetaDTO.getMedicamentos() != null) {
            for (CompletarCitaRequest.MedicamentoDTO medDTO : recetaDTO.getMedicamentos()) {
                Medicamento med = new Medicamento();
                med.setNombre(medDTO.getNombre());
                med.setPresentacion(medDTO.getPresentacion());
                med.setDosis(medDTO.getDosis());
                med.setFrecuencia(medDTO.getFrecuencia());
                med.setDuracion(medDTO.getDuracion());
                med.setCantidad(medDTO.getCantidad());
                med.setVia(medDTO.getVia());
                med.setReceta(recetaGuardada);
                medicamentoRepository.save(med);
            }
        }
    }

    // 5. Marcar cita como completada
    cita.setEstado(Cita.EstadoCita.completada);
    citaRepository.save(cita);
}
}