package com.hospital.hospital.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.hospital.model.entity.Paciente;

import java.time.LocalDateTime;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    @Query("SELECT p FROM Paciente p JOIN FETCH p.usuario u WHERE u.idUsuario = :idUsuario")
    Optional<Paciente> obtenerConUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT p FROM Paciente p JOIN FETCH p.usuario")
    List<Paciente> obtenerTodosConUsuario();

    @Query("""
    SELECT p FROM Paciente p
    JOIN FETCH p.usuario u
    WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))
       OR LOWER(p.apPaterno) LIKE LOWER(CONCAT('%', :filtro, '%'))
       OR LOWER(p.apMaterno) LIKE LOWER(CONCAT('%', :filtro, '%'))
       OR LOWER(p.nss) LIKE LOWER(CONCAT('%', :filtro, '%'))""")
    List<Paciente> buscarPacientes(@Param("filtro") String filtro);

}
