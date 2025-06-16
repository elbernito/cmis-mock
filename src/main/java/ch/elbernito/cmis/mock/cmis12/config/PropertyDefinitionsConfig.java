package ch.elbernito.cmis.mock.cmis12.config;

import ch.elbernito.cmis.mock.cmis12.dto.PropertyDefinitionsDto;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Configuration bean for all CMIS property definitions,
 * loaded from application.yaml under "cmis.property-definitions".
 */
@Configuration
@ConfigurationProperties(prefix = "cmis.property-definitions")
public class PropertyDefinitionsConfig extends PropertyDefinitionsDto {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
