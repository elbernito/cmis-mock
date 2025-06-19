package ch.elbernito.cmis.mock.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for ChangeLogModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangeLogDto {

    private String changelogId;
    private String objectId;
    private String changeType;
    private String summary;
    private LocalDateTime changeTime;
}
