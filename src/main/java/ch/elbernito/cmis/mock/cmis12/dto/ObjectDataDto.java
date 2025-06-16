package ch.elbernito.cmis.mock.cmis12.dto;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO for CMIS ObjectData (returned for getObject, getChildren, etc.).
 */
public class ObjectDataDto {
    private String objectId;
    private String objectTypeId;
    private Map<String, Object> properties;
    private List<String> policyIds;
    private List<String> relationshipIds;
    private List<RenditionDto> renditions;
    private AllowableActionsDto allowableActions;
    private AclDto acl;

    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }
    public String getObjectTypeId() { return objectTypeId; }
    public void setObjectTypeId(String objectTypeId) { this.objectTypeId = objectTypeId; }
    public Map<String, Object> getProperties() { return properties; }
    public void setProperties(Map<String, Object> properties) { this.properties = properties; }
    public List<String> getPolicyIds() { return policyIds; }
    public void setPolicyIds(List<String> policyIds) { this.policyIds = policyIds; }
    public List<String> getRelationshipIds() { return relationshipIds; }
    public void setRelationshipIds(List<String> relationshipIds) { this.relationshipIds = relationshipIds; }
    public List<RenditionDto> getRenditions() { return renditions; }
    public void setRenditions(List<RenditionDto> renditions) { this.renditions = renditions; }
    public AllowableActionsDto getAllowableActions() { return allowableActions; }
    public void setAllowableActions(AllowableActionsDto allowableActions) { this.allowableActions = allowableActions; }
    public AclDto getAcl() { return acl; }
    public void setAcl(AclDto acl) { this.acl = acl; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
