package com.survey.springboot.pwa.app.springboot_survey_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
})
public class SpringbootSurveyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSurveyAppApplication.class, args);
	}

}
