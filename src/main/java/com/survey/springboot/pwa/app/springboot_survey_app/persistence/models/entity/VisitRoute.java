package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Mapea la tabla rutas_visitas (compartida con el dashboard).
 * Se omite la relación id_programa porque la PWA nunca inserta rutas,
 * solo lee y actualiza el estado (id_programa no se toca en los UPDATE).
 */
@Entity
@Table(name = "rutas_visitas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rutas")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programacion_turnos")
    private ShiftSchedule shiftSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sujeto", nullable = false)
    private Subject subject;

    @Column(name = "fecha_programada")
    private LocalDate scheduledDate;

    @Column(name = "estado", length = 20)
    private String status;
}
