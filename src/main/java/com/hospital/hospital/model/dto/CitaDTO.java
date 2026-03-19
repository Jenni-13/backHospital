package com.hospital.hospital.model.dto;

import com.hospital.hospital.model.entity.Cita.EstadoCita;
import com.hospital.hospital.model.entity.Cita.TipoCita;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CitaDTO {

    private Integer idCita;
    private String folio;
    private LocalDate fecha;
    private LocalTime hora;
    private TipoCita tipo;
    private String motivo;
    private String dxPrincipal;
    private String dxDescripcion;
    private EstadoCita estado;

    // Solo datos básicos del paciente
    private String nombrePaciente;
    private String nss;
    private String curp;
    private LocalDate fechaNacimiento;

    public CitaDTO(com.hospital.hospital.model.entity.Cita cita) {
        this.idCita       = cita.getIdCita();
        this.folio        = cita.getFolio();
        this.fecha        = cita.getFecha();
        this.hora         = cita.getHora();
        this.tipo         = cita.getTipo();
        this.motivo       = cita.getMotivo();
        this.dxPrincipal  = cita.getDxPrincipal();
        this.dxDescripcion= cita.getDxDescripcion();
        this.estado       = cita.getEstado();

        if (cita.getPaciente() != null) {
            this.nombrePaciente = cita.getPaciente().getNombre()
                    + " " + cita.getPaciente().getApPaterno()
                    + " " + cita.getPaciente().getApMaterno();
            this.nss  = cita.getPaciente().getNss();
            this.curp = cita.getPaciente().getCurp();
            this.fechaNacimiento = cita.getPaciente().getFechaNacimiento();
        }
    }
}