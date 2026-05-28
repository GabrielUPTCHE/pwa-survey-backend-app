package com.survey.springboot.pwa.app.springboot_survey_app.services;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.RutaVisita;
import com.survey.springboot.pwa.app.springboot_survey_app.documents.Sujeto;
import com.survey.springboot.pwa.app.springboot_survey_app.documents.Turno;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.RutaVisitaResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.TurnoResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.repositories.RutaVisitaRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.repositories.SujetoRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.repositories.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurnoService {

    @Autowired private TurnoRepository turnoRepository;
    @Autowired private RutaVisitaRepository rutaVisitaRepository;
    @Autowired private SujetoRepository sujetoRepository;

    public List<TurnoResponse> getTurnosByFecha(String fecha) {
        List<Turno> turnos = turnoRepository.findByFecha(fecha);

        Map<String, Sujeto> sujetoByIdSujeto = sujetoRepository.findAll().stream()
                .collect(Collectors.toMap(Sujeto::getIdSujeto, s -> s, (a, b) -> a));

        return turnos.stream().map(turno -> {
            TurnoResponse tr = new TurnoResponse();
            tr.setProgramacionTurnos(turno.getProgramacionTurnos());
            tr.setHoraInicio(turno.getHoraInicio());
            tr.setHoraFin(turno.getHoraFin());

            List<RutaVisita> rutas = rutaVisitaRepository.findByIdTurno(turno.getId());
            List<RutaVisitaResponse> rutasResp = rutas.stream()
                    .map(rv -> toRutaResponse(rv, sujetoByIdSujeto))
                    .collect(Collectors.toList());
            tr.setRutasVisitas(rutasResp);
            return tr;
        }).collect(Collectors.toList());
    }

    public List<RutaVisitaResponse> getRecientes() {
        Map<String, Sujeto> sujetoByIdSujeto = sujetoRepository.findAll().stream()
                .collect(Collectors.toMap(Sujeto::getIdSujeto, s -> s, (a, b) -> a));

        return rutaVisitaRepository.findTop5ByOrderByFechaProgramadaDesc().stream()
                .map(rv -> toRutaResponse(rv, sujetoByIdSujeto))
                .collect(Collectors.toList());
    }

    private RutaVisitaResponse toRutaResponse(RutaVisita rv, Map<String, Sujeto> sujetoMap) {
        RutaVisitaResponse r = new RutaVisitaResponse();
        r.setIdRutas(rv.getIdRutas());
        r.setEstado(rv.getEstado());
        r.setFechaProgramada(rv.getFechaProgramada());

        Optional.ofNullable(sujetoMap.get(rv.getIdSujeto())).ifPresent(s -> {
            RutaVisitaResponse.SujetoInfo info = new RutaVisitaResponse.SujetoInfo();
            info.setRazonSocial(s.getRazonSocial());
            info.setBarrio(s.getBarrio());
            info.setDireccionFisica(s.getDireccionFisica());
            info.setNit(s.getNit());
            info.setZona(s.getZona());
            r.setSujeto(info);
        });
        return r;
    }
}
