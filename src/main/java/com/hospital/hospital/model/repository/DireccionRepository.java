package com.hospital.hospital.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.hospital.model.entity.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
}
