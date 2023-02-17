package com.backend.movie_recommender_backend;

import com.backend.movie_recommender_backend.init_db_entries.MovieContentInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class MovieRecommenderBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieRecommenderBackendApplication.class, args);
		try {
			new MovieContentInitializer().initialize();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public RestTemplate restTemplate(List<HttpMessageConverter<?>> messageConverters) {
		return new RestTemplate(messageConverters);
	}
	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}

}
