package ch.elbernito.cmis.mock.cmis12.dto;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO for all property definitions of CMIS object types.
 */
public class PropertyDefinitionsDto {
    private Map<String, List<PropertyDefinitionDto>> types;

    public Map<String, List<PropertyDefinitionDto>> getTypes() { return types; }
    public void setTypes(Map<String, List<PropertyDefinitionDto>> types) { this.types = types; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
