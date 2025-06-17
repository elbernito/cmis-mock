package ch.elbernito.cmis.mock.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for CMIS Object.
 */
@Data
public class ObjectDto {

    private Long id;
    private String objectId;
    private String name;
    private String createdBy;
    private LocalDateTime creationDate;
    private String lastModifiedBy;
    private LocalDateTime lastModificationDate;
    private String typeId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
