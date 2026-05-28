package com.survey.springboot.pwa.app.springboot_survey_app.repositories;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.Turno;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TurnoRepository extends MongoRepository<Turno, String> {
    List<Turno> findByFecha(String fecha);
}
