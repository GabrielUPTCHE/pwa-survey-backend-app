package com.survey.springboot.pwa.app.springboot_survey_app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Cors cors = new Cors();
    private Cookie cookie = new Cookie();

    @Data
    public static class Cors {
        /** Lista separada por comas de orígenes permitidos. Vacío = sin restricción cross-origin en dev. */
        private String allowedOrigins = "";
    }

    @Data
    public static class Cookie {
        /** SameSite de la cookie JWT: Lax (dev) | None (cross-origin prod) */
        private String sameSite = "Lax";
        /** true solo en prod HTTPS cross-origin */
        private boolean secure = false;
    }
}
