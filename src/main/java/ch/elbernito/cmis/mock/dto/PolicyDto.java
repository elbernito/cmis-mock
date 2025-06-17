package ch.elbernito.cmis.mock.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

@Data
public class PolicyDto {

    private Long id;
    private String policyId;
    private String name;
    private String policyText;
    private String createdBy;
    private String objectId;
    // this both not in a CMIS Spec!
    private List<DocumentDto> documents;
    private List<FolderDto> folders;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
