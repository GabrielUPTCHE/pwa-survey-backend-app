package com.survey.springboot.pwa.app.springboot_survey_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Representa cada visita dentro del turno y en /rutas-visitas/recientes.
 * El front accede a visita.id_rutas, visita.estado, visita.fecha_programada,
 * visita.sujeto.razon_social, visita.sujeto.barrio, etc.
 */
@Data
public class RutaVisitaResponse {

    @JsonProperty("id_rutas")
    private String idRutas;

    private String estado;

    @JsonProperty("fecha_programada")
    private String fechaProgramada;

    private SujetoInfo sujeto;

    @Data
    public static class SujetoInfo {
        @JsonProperty("id_sujeto")
        private String idSujeto;

        @JsonProperty("razon_social")
        private String razonSocial;

        private String barrio;

        @JsonProperty("direccion_fisica")
        private String direccionFisica;

        private String nit;
        private String zona;
    }
}
