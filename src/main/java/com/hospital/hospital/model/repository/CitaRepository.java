package com.hospital.hospital.model.repository;

import com.hospital.hospital.model.entity.Cita;
import com.hospital.hospital.model.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // Citas de un paciente
    List<Cita> findByPacienteIdPaciente(Integer idPaciente);

    // Contar citas de un médico en una fecha específica
    long countByMedicoAndFecha(Medico medico, LocalDate fecha);

    // Médicos por turno
    @Query("SELECT m FROM Medico m WHERE m.turno = :turno AND m.activo = true")
    List<Medico> findMedicosByTurno(@Param("turno") Medico.Turno turno);

    @Modifying
    @Query("UPDATE Cita c SET c.estado = :archivada WHERE c.estado = :cancelada AND c.fechaCreacion <= :fecha")
    void archivarCitasCanceladas(
            @Param("archivada") Cita.EstadoCita archivada,
            @Param("cancelada") Cita.EstadoCita cancelada,
            @Param("fecha") LocalDateTime fecha);

    // Citas por medico
    List<Cita> findByMedicoIdMedico(Integer idMedico);

}