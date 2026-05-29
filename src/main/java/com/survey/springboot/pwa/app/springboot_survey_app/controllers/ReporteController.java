package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/** GET /api/reportes/diario?fecha=YYYY-MM-DD — reporte del encuestador autenticado. */
@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/diario")
    public ResponseEntity<Map<String, Object>> diario(@RequestParam String fecha,
                                                      Authentication authentication) {
        String numeroIdentificacion = (String) authentication.getPrincipal();
        return ResponseEntity.ok(reporteService.diario(fecha, numeroIdentificacion));
    }
}
