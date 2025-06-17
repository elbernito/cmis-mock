package ch.elbernito.cmis.mock.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderTreeDto {
    private FolderDto folder;
    private List<FolderTreeDto> children;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}