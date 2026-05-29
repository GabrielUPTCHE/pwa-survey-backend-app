package com.survey.springboot.pwa.app.springboot_survey_app.services;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.LoginRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.UserResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.CrendentialEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.ERole;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.UserRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.security.CookieUtils;
import com.survey.springboot.pwa.app.springboot_survey_app.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private CookieUtils cookieUtils;
    @Autowired private PasswordEncoder passwordEncoder;

    /**
     * El front envía el numero_identificacion en el campo 'email' y la contraseña en 'password'.
     */
    @Transactional(readOnly = true)
    public UserResponse login(LoginRequest request, HttpServletResponse response) {
        UserEntity usuario = userRepository.findById(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!usuario.isState()) {
            throw new RuntimeException("Usuario inactivo");
        }

        CrendentialEntity cred = usuario.getCredential();
        if (cred == null || !passwordEncoder.matches(request.getPassword(), cred.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtils.generateToken(usuario.getNumberIdentification());
        cookieUtils.addTokenCookie(response, token);

        return toUserResponse(usuario);
    }

    public void logout(HttpServletResponse response) {
        cookieUtils.clearTokenCookie(response);
    }

    @Transactional
    public void changePassword(String numeroIdentificacion, String currentPassword, String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres");
        }

        UserEntity usuario = userRepository.findById(numeroIdentificacion)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CrendentialEntity cred = usuario.getCredential();
        if (cred == null || !passwordEncoder.matches(currentPassword, cred.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        cred.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public UserResponse verify(String numeroIdentificacion) {
        return userRepository.findById(numeroIdentificacion)
                .map(this::toUserResponse)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private UserResponse toUserResponse(UserEntity u) {
        ERole role = u.getRole() != null ? u.getRole().getName() : null;
        return new UserResponse(
                u.getName(),
                u.getLastName(),
                u.getNumberIdentification(),
                displayRole(role),
                roleSlug(role),
                u.getEmail()
        );
    }

    /** Slug usado por RoleRoute del front. */
    private String roleSlug(ERole role) {
        if (role == null) return "encuestador";
        return switch (role) {
            case ADMIN -> "admin";
            case ASSISTANT -> "supervisor";
            case SURVEYOR -> "encuestador";
        };
    }

    /** Texto para mostrar en UI. */
    private String displayRole(ERole role) {
        if (role == null) return "Encuestador";
        return switch (role) {
            case ADMIN -> "Administrador";
            case ASSISTANT -> "Supervisor";
            case SURVEYOR -> "Encuestador";
        };
    }
}
