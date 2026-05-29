package com.survey.springboot.pwa.app.springboot_survey_app.services;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.DocumentType;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.LegalDocument;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Subject;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.DocumentTypeRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.LegalDocumentRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.SubjectRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.VisitRouteRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentoLegalService {

    @Autowired private LegalDocumentRepository legalDocumentRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private DocumentTypeRepository documentTypeRepository;
    @Autowired private VisitRouteRepository visitRouteRepository;
    @Autowired private GridFsTemplate gridFsTemplate;
    @Autowired private GridFsOperations gridFsOperations;

    /**
     * Crea la encuesta (documento legal) en Postgres y, si viene id_rutas,
     * marca esa ruta de visita como Completada. Las evidencias se guardan en GridFS (Mongo).
     */
    @Transactional
    public Map<String, Object> crear(String idSujeto, String idTipoDocumento, String idActa,
                                     Double latitud, Double longitud,
                                     List<MultipartFile> evidencias, String idRutas) throws IOException {
        List<String> urls = new ArrayList<>();
        if (evidencias != null) {
            for (MultipartFile file : evidencias) {
                if (file.isEmpty()) continue;
                ObjectId fileId = gridFsTemplate.store(
                        file.getInputStream(),
                        file.getOriginalFilename(),
                        file.getContentType());
                urls.add("/api/evidencias/" + fileId.toHexString());
            }
        }

        Subject subject = subjectRepository.findById(Long.parseLong(idSujeto))
                .orElseThrow(() -> new RuntimeException("Sujeto no encontrado: " + idSujeto));
        DocumentType tipo = documentTypeRepository.findById(idTipoDocumento)
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado: " + idTipoDocumento));

        Long visitRouteId = (idRutas != null && !idRutas.isBlank()) ? Long.parseLong(idRutas) : null;

        LegalDocument doc = LegalDocument.builder()
                .id(idActa)
                .subject(subject)
                .documentType(tipo)
                .status("P")
                .evidenceUrl(String.join(",", urls))
                .latitude(latitud)
                .longitude(longitud)
                .visitRouteId(visitRouteId)
                .createdAt(Instant.now())
                .build();
        legalDocumentRepository.save(doc);

        if (visitRouteId != null) {
            visitRouteRepository.findById(visitRouteId).ifPresent(vr -> {
                vr.setStatus("Completada");
                visitRouteRepository.save(vr);
            });
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id_documento", doc.getId());
        result.put("estado", "Completada");
        result.put("url_evidencia", urls);
        return result;
    }

    public GridFsResource getEvidencia(String id) {
        GridFSFile file = gridFsTemplate.findOne(
                new Query(Criteria.where("_id").is(new ObjectId(id))));
        if (file == null) {
            throw new RuntimeException("Evidencia no encontrada: " + id);
        }
        return gridFsOperations.getResource(file);
    }
}
