package com.survey.springboot.pwa.app.springboot_survey_app.services;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.RutaVisitaResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.TurnoResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Subject;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ShiftScheduleRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.VisitRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnoService {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired private ShiftScheduleRepository shiftScheduleRepository;
    @Autowired private VisitRouteRepository visitRouteRepository;

    /** Turnos del encuestador autenticado para una fecha, con sus rutas de visita. */
    @Transactional(readOnly = true)
    public List<TurnoResponse> getTurnosByFecha(String fecha, String numeroIdentificacion) {
        LocalDate date = LocalDate.parse(fecha);
        List<ShiftSchedule> shifts =
                shiftScheduleRepository.findByUser_NumberIdentificationAndDate(numeroIdentificacion, date);

        return shifts.stream().map(ss -> {
            TurnoResponse tr = new TurnoResponse();
            tr.setProgramacionTurnos(String.valueOf(ss.getId()));
            tr.setHoraInicio(ss.getStartTime() != null ? ss.getStartTime().format(HORA) : null);
            tr.setHoraFin(ss.getEndTime() != null ? ss.getEndTime().format(HORA) : null);

            List<RutaVisitaResponse> rutas = visitRouteRepository.findByShiftSchedule_Id(ss.getId())
                    .stream().map(this::toRutaResponse).collect(Collectors.toList());
            tr.setRutasVisitas(rutas);
            return tr;
        }).collect(Collectors.toList());
    }

    /** Últimas visitas del encuestador autenticado (para la pantalla de inicio). */
    @Transactional(readOnly = true)
    public List<RutaVisitaResponse> getRecientes(String numeroIdentificacion) {
        return visitRouteRepository
                .findTop10ByShiftSchedule_User_NumberIdentificationOrderByScheduledDateDesc(numeroIdentificacion)
                .stream().map(this::toRutaResponse).collect(Collectors.toList());
    }

    private RutaVisitaResponse toRutaResponse(VisitRoute vr) {
        RutaVisitaResponse r = new RutaVisitaResponse();
        r.setIdRutas(String.valueOf(vr.getId()));
        r.setEstado(estado(vr));
        r.setFechaProgramada(vr.getScheduledDate() != null ? vr.getScheduledDate().toString() : null);

        Subject s = vr.getSubject();
        if (s != null) {
            RutaVisitaResponse.SujetoInfo info = new RutaVisitaResponse.SujetoInfo();
            info.setIdSujeto(String.valueOf(s.getId()));
            info.setRazonSocial(s.getBusinessName());
            info.setBarrio(s.getNeighborhood());
            info.setDireccionFisica(s.getPhysicalAddress());
            info.setNit(s.getNit());
            info.setZona(s.getZone());
            r.setSujeto(info);
        }
        return r;
    }

    /** Normaliza el estado a las etiquetas que espera la PWA. */
    private String estado(VisitRoute vr) {
        String raw = vr.getStatus() == null ? "" : vr.getStatus().trim().toLowerCase();
        if (raw.contains("complet")) return "Completado";
        if (raw.contains("progres") || raw.contains("curso")) return "En Progreso";
        if (raw.contains("futuro")) return "Futuro";
        return "Pendiente";
    }
}
