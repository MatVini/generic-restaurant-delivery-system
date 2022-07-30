package com.br.matvcirino.genericRestaurantDeliverySystem.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI apiInfo() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info()
						.title("Sistema de Delivery para Restaurante")
						.description("Aplicação com APIs para registro e controle de clientes e pedidos para restaurante."));
	}
}
