package ch.elbernito.cmis.mock.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetentionDto {

    private String retentionId;
    private String objectId;
    private LocalDateTime retentionStart;
    private LocalDateTime retentionEnd;
    private String description;
    private boolean active;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
