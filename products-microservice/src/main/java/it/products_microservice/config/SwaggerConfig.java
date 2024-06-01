package it.products_microservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservicio de productos",
                version = "1.0.0",
                description = "This is a microservice by Francisco Estrada"
        )
)
public class SwaggerConfig {


}