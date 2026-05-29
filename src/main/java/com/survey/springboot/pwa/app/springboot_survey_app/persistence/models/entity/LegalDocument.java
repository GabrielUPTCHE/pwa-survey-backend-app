package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "documentos_legales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegalDocument {

    @Id
    @Column(name = "id_documento", length = 20)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    private DocumentType documentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sujeto", nullable = false)
    private Subject subject;

    @Column(name = "estado", length = 2)
    private String status;

    @Column(name = "fecha_vencimiento")
    private LocalDate expirationDate;

    // URLs de evidencia en GridFS, separadas por coma (/api/evidencias/{id}).
    @Column(name = "url_evidencia")
    private String evidenceUrl;

    // Columnas nuevas para la ubicación en tiempo real capturada por la PWA.
    @Column(name = "latitud")
    private Double latitude;

    @Column(name = "longitud")
    private Double longitude;

    // Ruta de visita que originó la encuesta (columna simple, sin FK para evitar acoplar el insert).
    @Column(name = "id_rutas")
    private Long visitRouteId;

    @Column(name = "created_at")
    private Instant createdAt;
}
