package com.survey.springboot.pwa.app.springboot_survey_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthContext comprueba data.authenticated y data.user
 */
@Data
@AllArgsConstructor
public class VerifyResponse {
    private boolean authenticated;
    private UserResponse user;
}
