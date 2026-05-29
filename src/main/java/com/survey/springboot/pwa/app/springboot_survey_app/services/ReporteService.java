package com.survey.springboot.pwa.app.springboot_survey_app.services;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.LegalDocument;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Subject;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.LegalDocumentRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ShiftScheduleRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.VisitRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Reporte diario del encuestador autenticado: conteos + puntos recorridos (GPS). */
@Service
public class ReporteService {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");
    private static final ZoneId ZONE = ZoneId.systemDefault();

    @Autowired private ShiftScheduleRepository shiftScheduleRepository;
    @Autowired private VisitRouteRepository visitRouteRepository;
    @Autowired private LegalDocumentRepository legalDocumentRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> diario(String fecha, String numeroIdentificacion) {
        LocalDate date = LocalDate.parse(fecha);

        // Todas las rutas del encuestador para la fecha (a través de sus turnos).
        List<ShiftSchedule> shifts =
                shiftScheduleRepository.findByUser_NumberIdentificationAndDate(numeroIdentificacion, date);
        List<VisitRoute> rutas = shifts.stream()
                .flatMap(ss -> visitRouteRepository.findByShiftSchedule_Id(ss.getId()).stream())
                .toList();

        long total = rutas.size();
        List<VisitRoute> completadas = rutas.stream()
                .filter(vr -> vr.getStatus() != null && vr.getStatus().trim().toLowerCase().contains("complet"))
                .toList();
        long hechas = completadas.size();
        long faltan = total - hechas;

        // Puntos visitados: la encuesta de cada ruta completada (GPS en tiempo real).
        List<Long> idsRutas = completadas.stream().map(VisitRoute::getId).toList();
        Map<Long, LegalDocument> docPorRuta = idsRutas.isEmpty()
                ? Map.of()
                : legalDocumentRepository.findByVisitRouteIdIn(idsRutas).stream()
                    .collect(Collectors.toMap(LegalDocument::getVisitRouteId, d -> d, (a, b) -> a));

        List<Map<String, Object>> puntos = new ArrayList<>();
        for (VisitRoute vr : completadas) {
            Subject s = vr.getSubject();
            LegalDocument doc = docPorRuta.get(vr.getId());

            // Coordenadas: preferir las de la encuesta (GPS real); si no, las del sujeto registrado.
            Double lat = doc != null && doc.getLatitude() != null ? doc.getLatitude()
                    : (s != null ? s.getLatitude() : null);
            Double lng = doc != null && doc.getLongitude() != null ? doc.getLongitude()
                    : (s != null ? s.getLongitude() : null);
            if (lat == null || lng == null) continue;

            Map<String, Object> p = new LinkedHashMap<>();
            p.put("nombre", s != null ? s.getBusinessName() : "Visita");
            p.put("direccion", s != null ? s.getPhysicalAddress() : null);
            p.put("lat", lat);
            p.put("lng", lng);
            p.put("hora", doc != null && doc.getCreatedAt() != null
                    ? doc.getCreatedAt().atZone(ZONE).format(HORA) : null);
            puntos.add(p);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", total);
        result.put("completadas", hechas);
        result.put("faltan", faltan);
        result.put("puntos", puntos);
        return result;
    }
}
