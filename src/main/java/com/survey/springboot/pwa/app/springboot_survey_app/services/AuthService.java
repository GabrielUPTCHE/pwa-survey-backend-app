package com.survey.springboot.pwa.app.springboot_survey_app.services;

import com.survey.springboot.pwa.app.springboot_survey_app.documents.Usuario;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.LoginRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.UserResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.repositories.UsuarioRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.security.CookieUtils;
import com.survey.springboot.pwa.app.springboot_survey_app.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private CookieUtils cookieUtils;
    @Autowired private PasswordEncoder passwordEncoder;

    /**
     * El front envía el numero_identificacion en el campo 'email' y la contraseña en 'password'.
     * Devuelve el UserResponse para que AuthContext lo guarde en estado.
     */
    public UserResponse login(LoginRequest request, HttpServletResponse response) {
        Usuario usuario = usuarioRepository
                .findByNumeroIdentificacion(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getContrasena())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtils.generateToken(usuario.getNumeroIdentificacion());
        cookieUtils.addTokenCookie(response, token);

        return toUserResponse(usuario);
    }

    public void logout(HttpServletResponse response) {
        cookieUtils.clearTokenCookie(response);
    }

    public UserResponse verify(String numeroIdentificacion) {
        return usuarioRepository
                .findByNumeroIdentificacion(numeroIdentificacion)
                .map(this::toUserResponse)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private UserResponse toUserResponse(Usuario u) {
        return new UserResponse(
                u.getNombre(),
                u.getApellido(),
                u.getNumeroIdentificacion(),
                u.getNombreRol(),
                u.getIdRoles(),
                u.getCorreo()
        );
    }
}
