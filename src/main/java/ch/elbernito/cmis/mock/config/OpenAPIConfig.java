package ch.elbernito.cmis.mock.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI cmisOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CMIS Mock API")
                        .version("1.0.0")
                        .description("RESTful CMIS 1.2 Mock API implementation (Spring Boot 3.x)")
                        .contact(new Contact()
                                .name("elbernito")
                                .email("any@any.ch")
                                .url("https://github.com/elbernito/cmis-mock"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
