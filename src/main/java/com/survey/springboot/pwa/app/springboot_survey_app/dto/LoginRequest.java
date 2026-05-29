package com.survey.springboot.pwa.app.springboot_survey_app.dto;

import lombok.Data;

@Data
public class LoginRequest {
    /** El front envía el número de identificación en el campo 'email' */
    private String email;
    private String password;
}
