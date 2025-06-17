package ch.elbernito.cmis.mock.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Set;

@Data
public class AclDto {
    private Long id;
    private String principal;
    private String permission;
    private Set<Long> documentIds;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
