package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROLES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rol_id")
    @Getter @Setter
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_rol")
    @Getter @Setter
    private ERole name;
}
