package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.TurnoResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** GET /api/turnos?fecha=YYYY-MM-DD — turnos del encuestador autenticado. */
@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @GetMapping
    public ResponseEntity<List<TurnoResponse>> getByFecha(@RequestParam String fecha,
                                                          Authentication authentication) {
        String numeroIdentificacion = (String) authentication.getPrincipal();
        return ResponseEntity.ok(turnoService.getTurnosByFecha(fecha, numeroIdentificacion));
    }
}
