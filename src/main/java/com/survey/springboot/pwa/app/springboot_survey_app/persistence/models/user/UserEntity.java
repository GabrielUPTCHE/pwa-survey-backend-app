package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIOS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Column(name = "numero_identificacion", unique = true, length = 20, nullable = false)
    @Getter @Setter
    private String numberIdentification;

    @Getter @Setter
    @Column(name = "nombre", length = 50, nullable = false)
    private String name;

    @Getter @Setter
    @Column(name = "apellido", length = 50, nullable = false)
    private String lastName;

    @Getter @Setter
    @Column(name = "correo", unique = true, length = 70, nullable = false)
    private String email;

    @Column(name = "estado")
    @Getter @Setter
    private boolean state = true;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @Getter @Setter
    private CrendentialEntity credential;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "rol_id")
    private RoleEntity role;
}
