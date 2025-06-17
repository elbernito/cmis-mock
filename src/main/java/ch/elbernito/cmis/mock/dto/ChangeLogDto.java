package ch.elbernito.cmis.mock.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;

@Data
public class ChangeLogDto {
    private Long id;
    private String objectId;
    private String changeType;
    private String changedBy;
    private LocalDateTime changeTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
