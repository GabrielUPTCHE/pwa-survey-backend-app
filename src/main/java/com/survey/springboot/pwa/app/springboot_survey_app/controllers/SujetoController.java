package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.SujetoResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.services.SujetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** GET /api/sujetos?q= */
@RestController
@RequestMapping("/api/sujetos")
public class SujetoController {

    @Autowired
    private SujetoService sujetoService;

    @GetMapping
    public ResponseEntity<List<SujetoResponse>> search(@RequestParam(defaultValue = "") String q) {
        if (q.length() < 2) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(sujetoService.search(q));
    }
}
