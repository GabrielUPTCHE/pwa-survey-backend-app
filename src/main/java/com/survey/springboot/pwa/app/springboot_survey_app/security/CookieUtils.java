package com.survey.springboot.pwa.app.springboot_survey_app.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    @Value("${jwt.time.expiration:86400000}")
    private long expirationMs;

    public void addTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (expirationMs / 1000));
        // SameSite=Lax via header (Cookie API no lo expone directamente en servlet)
        response.addHeader("Set-Cookie",
                "token=" + token +
                "; Path=/" +
                "; HttpOnly" +
                "; Max-Age=" + (expirationMs / 1000) +
                "; SameSite=Lax");
    }

    public void clearTokenCookie(HttpServletResponse response) {
        response.addHeader("Set-Cookie",
                "token=" +
                "; Path=/" +
                "; HttpOnly" +
                "; Max-Age=0" +
                "; SameSite=Lax");
    }
}
