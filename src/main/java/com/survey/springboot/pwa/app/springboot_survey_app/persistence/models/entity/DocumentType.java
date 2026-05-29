package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_documento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentType {

    @Id
    @Column(name = "id_tipo_documento", length = 5)
    private String id;

    // El nombre de columna replica el del dashboard (incluye el typo "documeto").
    @Column(name = "nombre_documeto", length = 50)
    private String name;
}
