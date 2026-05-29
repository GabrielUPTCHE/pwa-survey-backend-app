package com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRouteRepository extends JpaRepository<VisitRoute, Long> {
    List<VisitRoute> findByShiftSchedule_Id(Long shiftScheduleId);
    List<VisitRoute> findTop10ByShiftSchedule_User_NumberIdentificationOrderByScheduledDateDesc(String numberIdentification);
}
