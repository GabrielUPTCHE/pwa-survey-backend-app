package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.services.DocumentoLegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/** POST /api/documentos-legales (multipart) */
@RestController
@RequestMapping("/api/documentos-legales")
public class DocumentoLegalController {

    @Autowired
    private DocumentoLegalService documentoLegalService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> crear(
            @RequestParam("id_sujeto") String idSujeto,
            @RequestParam("id_tipo_documento") String idTipoDocumento,
            @RequestParam("id_acta") String idActa,
            @RequestParam(value = "id_rutas", required = false) String idRutas,
            @RequestParam(value = "latitud", required = false) Double latitud,
            @RequestParam(value = "longitud", required = false) Double longitud,
            @RequestParam(value = "evidencias", required = false) List<MultipartFile> evidencias)
            throws IOException {

        Map<String, Object> saved = documentoLegalService.crear(
                idSujeto, idTipoDocumento, idActa, latitud, longitud, evidencias, idRutas);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
