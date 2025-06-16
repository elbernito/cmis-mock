package ch.elbernito.cmis.mock.cmis12.config;

import ch.elbernito.cmis.mock.cmis12.dto.AllowableActionsDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Loads AllowableActions from application.yaml for CMIS 1.2.
 */
@Configuration
@ConfigurationProperties(prefix = "cmis.allowable-actions.default")
public class AllowableActionsConfig extends AllowableActionsDto {
    // All properties are inherited.
}
