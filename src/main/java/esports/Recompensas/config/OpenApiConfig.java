package esports.Recompensas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Recompensas - Plataforma Esports")
                        .version("1.0.0")
                        .description("Microservicio encargado de gestionar el cálculo, división y asignación de premios a los equipos participantes de los torneos."));
    }

}
