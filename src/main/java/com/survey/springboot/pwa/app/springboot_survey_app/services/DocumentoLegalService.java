package com.survey.springboot.pwa.app.springboot_survey_app.services;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.survey.springboot.pwa.app.springboot_survey_app.documents.DocumentoLegal;
import com.survey.springboot.pwa.app.springboot_survey_app.repositories.DocumentoLegalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoLegalService {

    @Autowired private DocumentoLegalRepository documentoLegalRepository;
    @Autowired private GridFsTemplate gridFsTemplate;
    @Autowired private GridFsOperations gridFsOperations;

    public DocumentoLegal crear(String idSujeto, String idTipoDocumento, String idActa,
                                Double latitud, Double longitud,
                                List<MultipartFile> evidencias) throws IOException {
        List<String> urls = new ArrayList<>();

        if (evidencias != null) {
            for (MultipartFile file : evidencias) {
                if (file.isEmpty()) continue;
                ObjectId fileId = gridFsTemplate.store(
                        file.getInputStream(),
                        file.getOriginalFilename(),
                        file.getContentType()
                );
                urls.add("/api/evidencias/" + fileId.toHexString());
            }
        }

        DocumentoLegal doc = new DocumentoLegal();
        doc.setIdDocumento(UUID.randomUUID().toString());
        doc.setIdSujeto(idSujeto);
        doc.setIdTipoDocumento(idTipoDocumento);
        doc.setIdActa(idActa);
        doc.setEstado("Pendiente");
        doc.setLatitud(latitud);
        doc.setLongitud(longitud);
        doc.setUrlEvidencia(urls);

        return documentoLegalRepository.save(doc);
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
