package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.RutaVisitaResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** GET /api/rutas-visitas/recientes — del encuestador autenticado. */
@RestController
@RequestMapping("/api/rutas-visitas")
public class RutaVisitaController {

    @Autowired
    private TurnoService turnoService;

    @GetMapping("/recientes")
    public ResponseEntity<List<RutaVisitaResponse>> getRecientes(Authentication authentication) {
        String numeroIdentificacion = (String) authentication.getPrincipal();
        return ResponseEntity.ok(turnoService.getRecientes(numeroIdentificacion));
    }
}
