package ch.elbernito.cmis.mock.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

@Getter
@Setter
public class TypeDefinitionDto implements Serializable {
    private Long id;
    private String typeId;
    private String displayName;
    private String baseTypeId;
    private String description;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
