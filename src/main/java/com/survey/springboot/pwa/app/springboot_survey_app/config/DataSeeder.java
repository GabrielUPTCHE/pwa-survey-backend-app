package com.survey.springboot.pwa.app.springboot_survey_app.config;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.DocumentType;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.DocumentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Siembra los tipos de documento en Postgres (tabla compartida con el dashboard).
 * El resto de datos (usuarios, sujetos, turnos, rutas) los siembra el dashboard.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final DocumentTypeRepository documentTypeRepository;

    @Override
    public void run(String... args) {
        if (documentTypeRepository.count() > 0) return;

        documentTypeRepository.saveAll(List.of(
                tipo("TD001", "Cámara de Comercio"),
                tipo("TD002", "RUT"),
                tipo("TD003", "Concepto Sanitario"),
                tipo("TD004", "Uso de Suelos"),
                tipo("TD005", "Licencia de Func.")
        ));
    }

    private DocumentType tipo(String id, String nombre) {
        return DocumentType.builder().id(id).name(nombre).build();
    }
}
