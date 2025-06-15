// src/main/java/ch/elbernito/cmis/mock/config/SwaggerConfig.java
package ch.elbernito.cmis.mock.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI configuration.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI cmisOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("CMIS Mock API")
                        .description("Mock implementation of CMIS 1.2 endpoints")
                        .version("1.0"));
    }
}
