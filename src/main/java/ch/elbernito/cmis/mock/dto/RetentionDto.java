package ch.elbernito.cmis.mock.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for RetentionModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RetentionDto {

    private String retentionId;
    private String objectId;
    private String label;
    private LocalDateTime retentionUntil;
}
