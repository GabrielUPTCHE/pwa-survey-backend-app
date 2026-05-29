package com.survey.springboot.pwa.app.springboot_survey_app.security;

import com.survey.springboot.pwa.app.springboot_survey_app.config.AppProperties;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    @Value("${jwt.time.expiration:86400000}")
    private long expirationMs;

    @Autowired
    private AppProperties appProperties;

    public void addTokenCookie(HttpServletResponse response, String token) {
        String sameSite = appProperties.getCookie().getSameSite();
        boolean secure = appProperties.getCookie().isSecure();

        StringBuilder cookie = new StringBuilder()
                .append("token=").append(token)
                .append("; Path=/")
                .append("; HttpOnly")
                .append("; Max-Age=").append(expirationMs / 1000)
                .append("; SameSite=").append(sameSite);

        if (secure) {
            cookie.append("; Secure");
        }

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public void clearTokenCookie(HttpServletResponse response) {
        String sameSite = appProperties.getCookie().getSameSite();
        boolean secure = appProperties.getCookie().isSecure();

        StringBuilder cookie = new StringBuilder()
                .append("token=")
                .append("; Path=/")
                .append("; HttpOnly")
                .append("; Max-Age=0")
                .append("; SameSite=").append(sameSite);

        if (secure) {
            cookie.append("; Secure");
        }

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
