package com.treading.coin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * The type Open api config.
 */
@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

	/**
	 * Custom open api open api.
	 *
	 * @return the open api
	 */
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(
						new Info().title("Treading Coins API Docs").version("1.0.0").description("description")
								.license(new License().name("API License").url("localhost:8080")))
				.servers(
						List.of(new Server().url("localhost:8080")));
	}
}
