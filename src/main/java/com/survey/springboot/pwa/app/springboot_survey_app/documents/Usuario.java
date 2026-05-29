package com.survey.springboot.pwa.app.springboot_survey_app.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    @Indexed(unique = true)
    private String numeroIdentificacion;

    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;

    /** slug usado por RoleRoute: 'admin', 'supervisor', 'encuestador' */
    private String idRoles;

    /** texto para mostrar en UI: 'Administrador', 'Supervisor', 'Encuestador' */
    private String nombreRol;

    private boolean activo = true;
}
