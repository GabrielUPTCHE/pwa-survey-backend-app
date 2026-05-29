package com.survey.springboot.pwa.app.springboot_survey_app.services;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.SujetoCreateRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.SujetoResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Subject;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SujetoService {

    @Autowired private SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public List<SujetoResponse> search(String query) {
        return subjectRepository
                .findByBusinessNameContainingIgnoreCaseOrNitContainingIgnoreCaseOrNeighborhoodContainingIgnoreCase(
                        query, query, query)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SujetoResponse create(SujetoCreateRequest req) {
        Subject s = Subject.builder()
                .nit(req.getNit())
                .businessName(req.getRazonSocial())
                .legalRepresentation(req.getRepresentacionLegal())
                .physicalAddress(req.getDireccionFisica())
                .neighborhood(req.getBarrio())
                .zone(req.getZona())
                .latitude(req.getLatitud())
                .longitude(req.getLongitud())
                .censusStatus("Registrado")
                .createdAt(LocalDate.now())
                .build();
        return toResponse(subjectRepository.save(s));
    }

    private SujetoResponse toResponse(Subject s) {
        SujetoResponse r = new SujetoResponse();
        r.setIdSujeto(String.valueOf(s.getId()));
        r.setRazonSocial(s.getBusinessName());
        r.setDireccionFisica(s.getPhysicalAddress());
        r.setBarrio(s.getNeighborhood());
        r.setNit(s.getNit());
        r.setZona(s.getZone());
        r.setLatitud(s.getLatitude());
        r.setLongitud(s.getLongitude());
        return r;
    }
}
