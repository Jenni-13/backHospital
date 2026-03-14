package com.hospital.hospital.model.dto;

import com.hospital.hospital.model.entity.Medicamento.ViaAdministracion;
import com.hospital.hospital.model.enums.TipoDiagnostico;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CompletarCitaRequest {

    private SignosVitalesDTO signosVitales;
    private DiagnosticoDTO diagnostico;
    private RecetaDTO receta;


    public SignosVitalesDTO getSignosVitales() { return signosVitales; }
    public void setSignosVitales(SignosVitalesDTO signosVitales) { this.signosVitales = signosVitales; }

    public DiagnosticoDTO getDiagnostico() { return diagnostico; }
    public void setDiagnostico(DiagnosticoDTO diagnostico) { this.diagnostico = diagnostico; }

    public RecetaDTO getReceta() { return receta; }
    public void setReceta(RecetaDTO receta) { this.receta = receta; }


    public static class SignosVitalesDTO {
        private BigDecimal pesoKg;
        private BigDecimal tallaM;
        private String presionArterial;
        private Short frecuenciaCardiaca;
        private Short frecuenciaRespiratoria;
        private BigDecimal temperatura;
        private Byte spo2;
        private Short glucosa;

        public BigDecimal getPesoKg() { return pesoKg; }
        public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }
        public BigDecimal getTallaM() { return tallaM; }
        public void setTallaM(BigDecimal tallaM) { this.tallaM = tallaM; }
        public String getPresionArterial() { return presionArterial; }
        public void setPresionArterial(String presionArterial) { this.presionArterial = presionArterial; }
        public Short getFrecuenciaCardiaca() { return frecuenciaCardiaca; }
        public void setFrecuenciaCardiaca(Short frecuenciaCardiaca) { this.frecuenciaCardiaca = frecuenciaCardiaca; }
        public Short getFrecuenciaRespiratoria() { return frecuenciaRespiratoria; }
        public void setFrecuenciaRespiratoria(Short frecuenciaRespiratoria) { this.frecuenciaRespiratoria = frecuenciaRespiratoria; }
        public BigDecimal getTemperatura() { return temperatura; }
        public void setTemperatura(BigDecimal temperatura) { this.temperatura = temperatura; }
        public Byte getSpo2() { return spo2; }
        public void setSpo2(Byte spo2) { this.spo2 = spo2; }
        public Short getGlucosa() { return glucosa; }
        public void setGlucosa(Short glucosa) { this.glucosa = glucosa; }
    }

    public static class DiagnosticoDTO {
        private String cie10;
        private String descripcion;
        private TipoDiagnostico tipo;
        private String medicamentosBase;
        private String tratamiento;
        private String indicaciones;
        private String funAlta;

        public String getCie10() { return cie10; }
        public void setCie10(String cie10) { this.cie10 = cie10; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public TipoDiagnostico getTipo() { return tipo; }
        public void setTipo(TipoDiagnostico tipo) { this.tipo = tipo; }
        public String getMedicamentosBase() { return medicamentosBase; }
        public void setMedicamentosBase(String medicamentosBase) { this.medicamentosBase = medicamentosBase; }
        public String getTratamiento() { return tratamiento; }
        public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
        public String getIndicaciones() { return indicaciones; }
        public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
        public String getFunAlta() { return funAlta; }
        public void setFunAlta(String funAlta) { this.funAlta = funAlta; }
    }

    public static class RecetaDTO {
        private String folio;
        private LocalDate vencimiento;
        private List<MedicamentoDTO> medicamentos;

        public String getFolio() { return folio; }
        public void setFolio(String folio) { this.folio = folio; }
        public LocalDate getVencimiento() { return vencimiento; }
        public void setVencimiento(LocalDate vencimiento) { this.vencimiento = vencimiento; }
        public List<MedicamentoDTO> getMedicamentos() { return medicamentos; }
        public void setMedicamentos(List<MedicamentoDTO> medicamentos) { this.medicamentos = medicamentos; }
    }

    public static class MedicamentoDTO {
        private String nombre;
        private String presentacion;
        private String dosis;
        private String frecuencia;
        private String duracion;
        private Short cantidad;
        private ViaAdministracion via;

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getPresentacion() { return presentacion; }
        public void setPresentacion(String presentacion) { this.presentacion = presentacion; }
        public String getDosis() { return dosis; }
        public void setDosis(String dosis) { this.dosis = dosis; }
        public String getFrecuencia() { return frecuencia; }
        public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
        public String getDuracion() { return duracion; }
        public void setDuracion(String duracion) { this.duracion = duracion; }
        public Short getCantidad() { return cantidad; }
        public void setCantidad(Short cantidad) { this.cantidad = cantidad; }
        public ViaAdministracion getVia() { return via; }
        public void setVia(ViaAdministracion via) { this.via = via; }
    }
}