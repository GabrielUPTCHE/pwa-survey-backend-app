package com.survey.springboot.pwa.app.springboot_survey_app.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "sujetos")
public class Sujeto {

    @Id
    private String id;

    private String idSujeto;
    private String nit;
    private String razonSocial;
    private String representacionLegal;
    private Double latitud;
    private Double longitud;
    private String direccionFisica;
    private String barrio;
    private String zona;
    private String estadoCenso;
    private Instant createdAt = Instant.now();
}
