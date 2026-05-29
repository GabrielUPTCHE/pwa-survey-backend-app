package com.survey.springboot.pwa.app.springboot_survey_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SujetoCreateRequest {

    @JsonProperty("razon_social")
    private String razonSocial;

    private String nit;

    @JsonProperty("representacion_legal")
    private String representacionLegal;

    @JsonProperty("direccion_fisica")
    private String direccionFisica;

    private String barrio;
    private String zona;
    private Double latitud;
    private Double longitud;
}
