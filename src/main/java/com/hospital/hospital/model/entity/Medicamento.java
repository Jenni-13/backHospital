package com.hospital.hospital.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento")
    private Long idMedicamento;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "presentacion")
    private String presentacion;

    @Column(name = "dosis")
    private String dosis;

    @Column(name = "frecuencia")
    private String frecuencia;

    @Column(name = "duracion")
    private String duracion;

    @Column(name = "cantidad")
    private Short cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "via")
    private ViaAdministracion via;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;

    public enum ViaAdministracion {
        oral, intravenosa, intramuscular, subcutanea, topica, inhalatoria, sublingual
    }
}