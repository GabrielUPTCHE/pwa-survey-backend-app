package com.survey.springboot.pwa.app.springboot_survey_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Objeto user que espera el front:
 *   user.nombre, user.apellido, user.numero_identificacion,
 *   user.nombre_rol, user.id_roles (slug para RoleRoute)
 */
@Data
@AllArgsConstructor
public class UserResponse {

    private String nombre;
    private String apellido;

    @JsonProperty("numero_identificacion")
    private String numeroIdentificacion;

    @JsonProperty("nombre_rol")
    private String nombreRol;

    @JsonProperty("id_roles")
    private String idRoles;

    private String correo;
}
