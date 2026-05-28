package com.survey.springboot.pwa.app.springboot_survey_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SujetoResponse {

    @JsonProperty("id_sujeto")
    private String idSujeto;

    @JsonProperty("razon_social")
    private String razonSocial;

    @JsonProperty("direccion_fisica")
    private String direccionFisica;

    private String barrio;
    private String nit;
    private String zona;
    private Double latitud;
    private Double longitud;
}
