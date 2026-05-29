package com.survey.springboot.pwa.app.springboot_survey_app.services;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.Sujeto;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.SujetoResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.repositories.SujetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SujetoService {

    @Autowired private SujetoRepository sujetoRepository;

    public List<SujetoResponse> search(String query) {
        return sujetoRepository
                .findByRazonSocialContainingIgnoreCaseOrNitContainingIgnoreCaseOrBarrioContainingIgnoreCase(
                        query, query, query)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SujetoResponse toResponse(Sujeto s) {
        SujetoResponse r = new SujetoResponse();
        r.setIdSujeto(s.getIdSujeto());
        r.setRazonSocial(s.getRazonSocial());
        r.setDireccionFisica(s.getDireccionFisica());
        r.setBarrio(s.getBarrio());
        r.setNit(s.getNit());
        r.setZona(s.getZona());
        r.setLatitud(s.getLatitud());
        r.setLongitud(s.getLongitud());
        return r;
    }
}
