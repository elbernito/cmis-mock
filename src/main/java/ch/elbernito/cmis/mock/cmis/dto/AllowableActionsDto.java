package ch.elbernito.cmis.mock.cmis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for listing allowed CMIS actions for an object.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllowableActionsDto {
    private String objectId;
    private Map<String, Boolean> actions;
}
