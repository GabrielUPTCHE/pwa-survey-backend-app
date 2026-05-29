package com.survey.springboot.pwa.app.springboot_survey_app.repositories;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.TipoDocumento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TipoDocumentoRepository extends MongoRepository<TipoDocumento, String> {
}
