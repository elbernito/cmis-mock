package ch.elbernito.cmis.mock.cmis12.config;

import ch.elbernito.cmis.mock.cmis12.dto.RepositoryInfoDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Loads RepositoryInfo from application.yaml for CMIS 1.2.
 */
@Configuration
@ConfigurationProperties(prefix = "cmis.repository")
public class RepositoryInfoConfig extends RepositoryInfoDto {
    // All properties are inherited.
}