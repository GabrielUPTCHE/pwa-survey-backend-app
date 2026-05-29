package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.TipoDocumentoResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/** GET /api/tipos-documento */
@RestController
@RequestMapping("/api/tipos-documento")
public class TipoDocumentoController {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @GetMapping
    public ResponseEntity<List<TipoDocumentoResponse>> getAll() {
        List<TipoDocumentoResponse> result = documentTypeRepository.findAll().stream()
                .map(t -> {
                    TipoDocumentoResponse r = new TipoDocumentoResponse();
                    r.setIdTipoDocumento(t.getId());
                    r.setNombreDocumento(t.getName());
                    return r;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
