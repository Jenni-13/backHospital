package com.hospital.hospital.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    @Column(name = "folio", length = 30, unique = true)
    private String folio;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoCita tipo = TipoCita.primera_vez;

    @Column(name = "motivo", length = 200)
    private String motivo;

    @Column(name = "dx_principal", length = 10)
    private String dxPrincipal;

    @Column(name = "dx_descripcion", length = 255)
    private String dxDescripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCita estado = EstadoCita.pendiente;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    public enum EstadoCita {
        pendiente, proceso, cancelada, completada, archivada
    }

    public enum TipoCita {
        primera_vez, subsecuente, urgencia, referida, archivada
    }
}