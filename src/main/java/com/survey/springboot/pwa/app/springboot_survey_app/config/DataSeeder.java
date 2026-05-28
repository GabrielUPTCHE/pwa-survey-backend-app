package com.survey.springboot.pwa.app.springboot_survey_app.config;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.*;
import com.survey.springboot.pwa.app.springboot_survey_app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TipoDocumentoRepository tipoDocumentoRepository;
    @Autowired private SujetoRepository sujetoRepository;
    @Autowired private TurnoRepository turnoRepository;
    @Autowired private RutaVisitaRepository rutaVisitaRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedUsuarios();
        seedTiposDocumento();
        seedSujetos();
        seedTurnosYRutas();
    }

    private void seedUsuarios() {
        if (usuarioRepository.count() > 0) return;

        usuarioRepository.saveAll(List.of(
                usuario("1000000001", "Carlos", "Ramírez", "admin@censo.co",
                        "admin123", "admin", "Administrador"),
                usuario("1000000002", "Laura", "Gómez", "supervisor@censo.co",
                        "super123", "supervisor", "Supervisor"),
                usuario("1000000003", "Juan", "Pérez", "encuestador@censo.co",
                        "encuesta123", "encuestador", "Encuestador")
        ));
    }

    private Usuario usuario(String id, String nombre, String apellido, String correo,
                            String pass, String idRoles, String nombreRol) {
        Usuario u = new Usuario();
        u.setNumeroIdentificacion(id);
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setCorreo(correo);
        u.setContrasena(passwordEncoder.encode(pass));
        u.setIdRoles(idRoles);
        u.setNombreRol(nombreRol);
        u.setActivo(true);
        return u;
    }

    private void seedTiposDocumento() {
        if (tipoDocumentoRepository.count() > 0) return;

        tipoDocumentoRepository.saveAll(List.of(
                tipo("TD001", "Cámara de Comercio"),
                tipo("TD002", "RUT"),
                tipo("TD003", "Concepto Sanitario"),
                tipo("TD004", "Uso de Suelos"),
                tipo("TD005", "Licencia de Funcionamiento")
        ));
    }

    private TipoDocumento tipo(String id, String nombre) {
        TipoDocumento t = new TipoDocumento();
        t.setIdTipoDocumento(id);
        t.setNombreDocumento(nombre);
        return t;
    }

    private void seedSujetos() {
        if (sujetoRepository.count() > 0) return;

        sujetoRepository.saveAll(List.of(
                sujeto("S001", "900.123.456-1", "Ferretería El Tornillo", "Pedro García",
                        5.6324, -73.5678, "Calle 15 # 8-23", "Centro", "Zona 1"),
                sujeto("S002", "800.234.567-2", "Panadería La Espiga", "María López",
                        5.6351, -73.5701, "Carrera 10 # 12-45", "San Francisco", "Zona 2"),
                sujeto("S003", "700.345.678-3", "Droguería Salud Total", "Luis Martínez",
                        5.6289, -73.5643, "Calle 20 # 6-12", "El Lago", "Zona 1"),
                sujeto("S004", "600.456.789-4", "Restaurante Mi Tierra", "Ana Rodríguez",
                        5.6412, -73.5724, "Av Principal # 3-67", "La Esperanza", "Zona 3"),
                sujeto("S005", "500.567.890-5", "Papelería Estudiantil", "Jorge Silva",
                        5.6378, -73.5659, "Carrera 7 # 18-30", "Villa Olímpica", "Zona 2"),
                sujeto("S006", "400.678.901-6", "Miscelánea El Punto", "Sandra Castro",
                        5.6445, -73.5698, "Calle 25 # 4-89", "Boyacá Real", "Zona 3"),
                sujeto("S007", "300.789.012-7", "Taller Mecánico Rápido", "Roberto Torres",
                        5.6302, -73.5712, "Diagonal 8 # 22-11", "Cooservicios", "Zona 1"),
                sujeto("S008", "200.890.123-8", "Supermercado El Ahorro", "Patricia Mora",
                        5.6467, -73.5735, "Calle 30 # 11-55", "Terminal", "Zona 4")
        ));
    }

    private Sujeto sujeto(String idSujeto, String nit, String razonSocial, String repLegal,
                          double lat, double lng, String dir, String barrio, String zona) {
        Sujeto s = new Sujeto();
        s.setIdSujeto(idSujeto);
        s.setNit(nit);
        s.setRazonSocial(razonSocial);
        s.setRepresentacionLegal(repLegal);
        s.setLatitud(lat);
        s.setLongitud(lng);
        s.setDireccionFisica(dir);
        s.setBarrio(barrio);
        s.setZona(zona);
        s.setEstadoCenso("Pendiente");
        return s;
    }

    private void seedTurnosYRutas() {
        if (turnoRepository.count() > 0) return;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate hoy = LocalDate.now();

        // Turno hoy mañana
        Turno t1 = turno("T001", hoy.format(fmt), "08:00", "12:00", "1000000003", "Inspección");
        turnoRepository.save(t1);

        // Turno hoy tarde
        Turno t2 = turno("T002", hoy.format(fmt), "14:00", "17:00", "1000000003", "Inspección");
        turnoRepository.save(t2);

        // Turno ayer
        Turno t3 = turno("T003", hoy.minusDays(1).format(fmt), "08:00", "12:00", "1000000003", "Inspección");
        turnoRepository.save(t3);

        // Turno mañana
        Turno t4 = turno("T004", hoy.plusDays(1).format(fmt), "09:00", "13:00", "1000000003", "Inspección");
        turnoRepository.save(t4);

        // Rutas para turno hoy mañana
        rutaVisitaRepository.saveAll(List.of(
                ruta("RV001", t1.getId(), "S001", hoy.format(fmt), "En Progreso"),
                ruta("RV002", t1.getId(), "S002", hoy.format(fmt), "Pendiente"),
                ruta("RV003", t1.getId(), "S003", hoy.format(fmt), "Pendiente")
        ));

        // Rutas para turno hoy tarde
        rutaVisitaRepository.saveAll(List.of(
                ruta("RV004", t2.getId(), "S004", hoy.format(fmt), "Pendiente"),
                ruta("RV005", t2.getId(), "S005", hoy.format(fmt), "Futuro")
        ));

        // Rutas para turno ayer (completadas — aparecen en "Actividad reciente")
        rutaVisitaRepository.saveAll(List.of(
                ruta("RV006", t3.getId(), "S006", hoy.minusDays(1).format(fmt), "Completado"),
                ruta("RV007", t3.getId(), "S007", hoy.minusDays(1).format(fmt), "Completado")
        ));

        // Rutas para turno mañana
        rutaVisitaRepository.saveAll(List.of(
                ruta("RV008", t4.getId(), "S008", hoy.plusDays(1).format(fmt), "Futuro")
        ));
    }

    private Turno turno(String pid, String fecha, String inicio, String fin,
                        String numeroId, String tipo) {
        Turno t = new Turno();
        t.setProgramacionTurnos(pid);
        t.setFecha(fecha);
        t.setHoraInicio(inicio);
        t.setHoraFin(fin);
        t.setNumeroIdentificacion(numeroId);
        t.setTipoActividad(tipo);
        return t;
    }

    private RutaVisita ruta(String idRutas, String idTurno, String idSujeto,
                            String fechaProgramada, String estado) {
        RutaVisita rv = new RutaVisita();
        rv.setIdRutas(idRutas);
        rv.setIdTurno(idTurno);
        rv.setIdSujeto(idSujeto);
        rv.setFechaProgramada(fechaProgramada);
        rv.setEstado(estado);
        return rv;
    }
}
