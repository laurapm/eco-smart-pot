package com.rainforesteco.spring.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS: Cross-Origin Resource Sharing. It returns an error when 
 * trying to access from Angular 10. Therefore, this class.
 * 
 * @author pablo
 * @creationDate 19/08/2020
 * 
 */
@Configuration
@EnableWebMvc
@Profile("production")
public class CorsConfig implements WebMvcConfigurer
{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowCredentials(false)
				;
	}
}
