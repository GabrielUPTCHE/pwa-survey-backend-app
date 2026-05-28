package com.survey.springboot.pwa.app.springboot_survey_app.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")
public class ControllerPrueba {
    @GetMapping
    public ResponseEntity<String> prueba() {
        return ResponseEntity.ok("prueba ok");
    }
}
