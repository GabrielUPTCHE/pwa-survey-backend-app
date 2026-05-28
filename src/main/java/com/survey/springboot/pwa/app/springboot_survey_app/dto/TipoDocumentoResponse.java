package com.survey.springboot.pwa.app.springboot_survey_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TipoDocumentoResponse {

    @JsonProperty("id_tipo_documento")
    private String idTipoDocumento;

    @JsonProperty("nombre_documento")
    private String nombreDocumento;
}
