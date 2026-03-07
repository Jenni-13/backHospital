package com.hospital.hospital.model.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PacienteDTO {
    private Integer idPaciente;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String curp;
    private String nss;
    private LocalDate fechaNacimiento;
    private String sexo;
    private String tipoSangre;
    private String telefono;
    private String telefonoEmergencias;
    private LocalDateTime fechaAlta;
    private String correo;
    private String estadoUsuario;

    // Dirección
    private DireccionDTO direccion;

    // Unidad médica
    private UnidadMedicaDTO unidadMedica;

    @Data
    public static class DireccionDTO {
        private String calle;
        private String numExt;
        private String numInt;
        private String colonia;
        private String cp;
        private String localidad;
        private String estado;
    }

    @Data
    public static class UnidadMedicaDTO {
        private Short idUm;
        private String clave;
        private String nombre;
        private String municipio;
        private String estado;
        private String delegacion;
    }
}