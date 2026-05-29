package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.LoginRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.UserResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.VerifyResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /** POST /api/auth/login — usado por auth.service.js */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request,
                                              HttpServletResponse response) {
        UserResponse user = authService.login(request, response);
        return ResponseEntity.ok(user);
    }

    /** GET /api/auth/verify — usado por AuthContext (fetch directo con VITE_PATH) */
    @GetMapping("/verify")
    public ResponseEntity<VerifyResponse> verify(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        String numeroIdentificacion = (String) authentication.getPrincipal();
        UserResponse user = authService.verify(numeroIdentificacion);
        return ResponseEntity.ok(new VerifyResponse(true, user));
    }

    /** POST /api/auth/logout — usado por AuthContext (fetch directo con VITE_PATH) */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok().build();
    }
}
