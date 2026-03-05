package com.hospital.hospital.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class RecetaRequestDTO {

    @NotNull(message = "El id de la cita es obligatorio")
    private Integer idCita;

    @NotBlank(message = "El folio es obligatorio")
    @Size(max = 30)
    private String folio;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    private LocalDate vencimiento;

    @NotEmpty(message = "Debe incluir al menos un medicamento")
    @Valid
    private List<MedicamentoRequestDTO> medicamentos;

    @Data
    public static class MedicamentoRequestDTO {

        @NotBlank(message = "El nombre del medicamento es obligatorio")
        private String nombre;

        private String presentacion;

        private String dosis;

        private String frecuencia;

        private String duracion;

        private Short cantidad;

        @NotNull(message = "La vía de administración es obligatoria")
        private String via; // nombre del enum: oral, intravenosa, etc.
    }
}