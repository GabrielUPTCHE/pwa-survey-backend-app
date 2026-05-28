package com.survey.springboot.pwa.app.springboot_survey_app.repositories;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByNumeroIdentificacion(String numeroIdentificacion);
}
