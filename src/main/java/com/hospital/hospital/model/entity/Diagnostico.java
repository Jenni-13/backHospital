package com.hospital.hospital.model.entity;


import com.hospital.hospital.model.converter.ListToStringConverter;
import com.hospital.hospital.model.enums.TipoDiagnostico;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList; 

@Entity
@Table(name = "diagnostico")
public class Diagnostico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_diagnostico")
    private Long idDiagnostico;

    @Column(name = "cie10", length = 10, nullable = false, unique = true)
    private String cie10;

    @Column(name = "descripcion", length = 255, nullable = false, unique = true)
    private String descripcion;    

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoDiagnostico tipo;
     
    @Column(columnDefinition = "TEXT")
    private String medicamentos_base;
 
    @Column(columnDefinition = "TEXT")
    private String tratamiento;

    @Column(columnDefinition = "TEXT")
    private String indicaciones;

    @Column(columnDefinition = "TEXT")
    private String fun_alta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", referencedColumnName = "id_cita", insertable = false, updatable = false)
    private Cita cita;


    public Diagnostico() {}

    public Long getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(Long idDiagnostico) { this.idDiagnostico = idDiagnostico; }

    public String getCie10() { return cie10; }
    public void setCie10(String cie10) { this.cie10 = cie10; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public TipoDiagnostico getTipo() { return tipo; }
    public void setTipo(TipoDiagnostico tipo) { this.tipo = tipo; }

    public String getMedicamentos_base() { return medicamentos_base; }
    public void setMedicamentos_base(String medicamentos_base) { this.medicamentos_base = medicamentos_base; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    public String getFun_alta() { return fun_alta; }
    public void setFun_alta(String fun_alta) { this.fun_alta = fun_alta; }

    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }

}