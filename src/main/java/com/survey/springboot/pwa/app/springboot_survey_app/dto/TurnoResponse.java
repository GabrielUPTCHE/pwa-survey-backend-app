package com.survey.springboot.pwa.app.springboot_survey_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * El front itera turnos y aplana: turnos.flatMap(t => t.rutas_visitas.map(v => ({...v, turno: t})))
 * Necesita turno.hora_inicio, turno.hora_fin y turno.rutas_visitas[].
 */
@Data
public class TurnoResponse {

    @JsonProperty("programacion_turnos")
    private String programacionTurnos;

    @JsonProperty("hora_inicio")
    private String horaInicio;

    @JsonProperty("hora_fin")
    private String horaFin;

    @JsonProperty("rutas_visitas")
    private List<RutaVisitaResponse> rutasVisitas;
}
