package com.proyecto.Perfulandia.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI perfulandiaOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Perfulandia SPA GRUPO 2")
                .version("1.0")
                .description("Documentación de la API REST para la gestión de Perfulandia del grupo 2"));
    }


}
