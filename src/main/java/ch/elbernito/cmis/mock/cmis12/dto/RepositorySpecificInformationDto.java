package ch.elbernito.cmis.mock.cmis12.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RepositorySpecificInformationDto {
    private String additionalInfo;

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
