package com.hospital.hospital.model.repository;

import com.hospital.hospital.model.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    @Query("SELECT r FROM Receta r WHERE r.cita.paciente.usuario.idUsuario = :idUsuario")
    List<Receta> findByUsuarioId(@Param("idUsuario") Integer idUsuario);

    boolean existsByFolio(String folio);
}