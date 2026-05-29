package com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
