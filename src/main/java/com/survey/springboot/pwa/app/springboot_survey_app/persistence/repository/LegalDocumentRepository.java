package com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.LegalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LegalDocumentRepository extends JpaRepository<LegalDocument, String> {
    List<LegalDocument> findByVisitRouteIdIn(Collection<Long> visitRouteIds);
}
