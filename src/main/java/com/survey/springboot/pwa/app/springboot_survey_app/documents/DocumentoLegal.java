package com.survey.springboot.pwa.app.springboot_survey_app.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "documentos_legales")
public class DocumentoLegal {

    @Id
    private String id;

    private String idDocumento;
    private String idTipoDocumento;
    private String idSujeto;
    private String idActa;
    private String estado;
    private String fechaVencimiento;

    /** Lista de URLs /api/evidencias/{gridFsId} */
    private List<String> urlEvidencia;

    private Double latitud;
    private Double longitud;
    private Instant createdAt = Instant.now();
}
