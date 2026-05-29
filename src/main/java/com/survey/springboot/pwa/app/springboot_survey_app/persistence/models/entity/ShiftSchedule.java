package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "programacion_turnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programacion_turnos")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_identificacion", referencedColumnName = "numero_identificacion", nullable = false)
    private UserEntity user;

    @Column(name = "fecha")
    private LocalDate date;

    @Column(name = "hora_inicio")
    private LocalTime startTime;

    @Column(name = "hora_fin")
    private LocalTime endTime;

    @Column(name = "tipo_actividad", length = 255)
    private String activityType;
}
