package com.survey.springboot.pwa.app.springboot_survey_app.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tipos_documento")
public class TipoDocumento {

    @Id
    private String id;

    private String idTipoDocumento;
    private String nombreDocumento;
}
