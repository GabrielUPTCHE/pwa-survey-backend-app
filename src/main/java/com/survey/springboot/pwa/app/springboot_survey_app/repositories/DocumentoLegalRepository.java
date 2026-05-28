package com.survey.springboot.pwa.app.springboot_survey_app.repositories;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.DocumentoLegal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentoLegalRepository extends MongoRepository<DocumentoLegal, String> {
}
