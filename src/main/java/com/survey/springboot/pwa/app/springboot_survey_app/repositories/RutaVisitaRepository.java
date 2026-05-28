package com.survey.springboot.pwa.app.springboot_survey_app.repositories;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.RutaVisita;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RutaVisitaRepository extends MongoRepository<RutaVisita, String> {
    List<RutaVisita> findByIdTurno(String idTurno);
    List<RutaVisita> findTop5ByOrderByFechaProgramadaDesc();
}
