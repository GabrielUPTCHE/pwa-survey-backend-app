package com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, String> {
}
