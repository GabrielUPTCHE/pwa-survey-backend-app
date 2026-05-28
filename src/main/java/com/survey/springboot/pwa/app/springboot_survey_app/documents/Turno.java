package com.survey.springboot.pwa.app.springboot_survey_app.documents;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.RutaVisita;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "turnos")
public class Turno {

    @Id
    private String id;

    private String programacionTurnos;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String numeroIdentificacion;
    private String tipoActividad;

    /** Rutas de visita asociadas a este turno (embebidas para la respuesta del endpoint). */
    private List<RutaVisita> rutasVisitas;
}
