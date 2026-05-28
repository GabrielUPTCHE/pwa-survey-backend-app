package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.services.DocumentoLegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** GET /api/evidencias/{id} — sirve binario de GridFS. Público (sin auth). */
@RestController
@RequestMapping("/api/evidencias")
public class EvidenciaController {

    @Autowired
    private DocumentoLegalService documentoLegalService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getEvidencia(@PathVariable String id) {
        GridFsResource resource = documentoLegalService.getEvidencia(id);
        String contentType = resource.getContentType() != null
                ? resource.getContentType()
                : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }
}
