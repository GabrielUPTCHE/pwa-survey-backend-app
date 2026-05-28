package com.survey.springboot.pwa.app.springboot_survey_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/** El front lee err.message o err.error para mostrar alertas */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
}
