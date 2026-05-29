package com.survey.springboot.pwa.app.springboot_survey_app.repositories;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.Sujeto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SujetoRepository extends MongoRepository<Sujeto, String> {
    List<Sujeto> findByRazonSocialContainingIgnoreCaseOrNitContainingIgnoreCaseOrBarrioContainingIgnoreCase(
            String razonSocial, String nit, String barrio);
}
