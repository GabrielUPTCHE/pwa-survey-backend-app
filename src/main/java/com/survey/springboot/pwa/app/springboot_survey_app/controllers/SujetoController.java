package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.SujetoCreateRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.SujetoResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.services.SujetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** GET /api/sujetos?q=  |  POST /api/sujetos (registrar local) */
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

    @PostMapping
    public ResponseEntity<SujetoResponse> create(@RequestBody SujetoCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sujetoService.create(request));
    }
}
