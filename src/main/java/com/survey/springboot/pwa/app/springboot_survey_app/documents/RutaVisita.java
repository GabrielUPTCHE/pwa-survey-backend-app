package com.survey.springboot.pwa.app.springboot_survey_app.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rutas_visitas")
public class RutaVisita {

    @Id
    private String id;

    private String idRutas;
    private String idTurno;
    private String idSujeto;
    private String fechaProgramada;

    /** 'En Progreso' | 'Pendiente' | 'Completado' | 'Futuro' */
    private String estado;

    /** Sujeto embebido para respuestas (poblado en el service) */
    private SujetoEmbebido sujeto;

    @Data
    public static class SujetoEmbebido {
        private String razonSocial;
        private String barrio;
        private String direccionFisica;
        private String nit;
        private String zona;
    }
}
