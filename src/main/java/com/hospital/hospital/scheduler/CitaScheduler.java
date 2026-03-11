package com.hospital.hospital.scheduler;

import com.hospital.hospital.model.entity.Cita;
import com.hospital.hospital.model.repository.CitaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CitaScheduler {

    private final CitaRepository citaRepository;

    // Corre todos los días a medianoche
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void archivarCitasCanceladas() {
        LocalDateTime hace2Dias = LocalDateTime.now().minusDays(2);
        log.info(">>> Archivando citas canceladas anteriores a: {}", hace2Dias);
        citaRepository.archivarCitasCanceladas(
                Cita.EstadoCita.archivada,
                Cita.EstadoCita.cancelada,
                hace2Dias);
        log.info(">>> Archivado completado");
    }
}