package ch.elbernito.cmis.mock.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi cmisApi() {
        return GroupedOpenApi.builder()
                .group("CMIS API")
                .displayName("CMIS API")
                .pathsToMatch("/api/cmis/**")
                .addOpenApiCustomizer(openApi -> openApi.info(
                        new Info()
                                .title("CMIS Mock API")
                                .description("API documentation for all CMIS endpoints (CMIS 1.2 standard).")
                                .version("1.2")
                                .contact(new Contact()
                                        .name("CMIS Mock Maintainer")
                                        .email("info@cmis-mock.local"))
                                .license(new License()
                                        .name("Apache 2.0")
                                        .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                ))
                .build();
    }

    @Bean
    public GroupedOpenApi crudApi() {
        return GroupedOpenApi.builder()
                .group("CRUD API")
                .displayName("CRUD API")
                .pathsToMatch("/api/crud/**")
                .addOpenApiCustomizer(openApi -> openApi.info(
                        new Info()
                                .title("General CRUD API")
                                .description("API documentation for all generic CRUD endpoints.")
                                .version("v1")
                                .contact(new Contact()
                                        .name("CRUD API Maintainer")
                                        .email("support@crudapi.local"))
                                .license(new License()
                                        .name("MIT")
                                        .url("https://opensource.org/licenses/MIT"))
                ))
                .build();
    }
}
