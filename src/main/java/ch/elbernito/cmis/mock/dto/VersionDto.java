package ch.elbernito.cmis.mock.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Getter
@Setter
public class VersionDto {

    private Long id;
    private Long documentId;
    private String versionLabel;
    private boolean isMajorVersion;
    private LocalDateTime creationDate;
    private String createdBy;
    private String comment;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
