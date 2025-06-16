package ch.elbernito.cmis.mock.cmis12.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO for AllowableActions for CMIS objects.
 */
public class AllowableActionsDto {
    private Boolean canCreateDocument;
    private Boolean canUpdateProperties;
    private Boolean canDeleteObject;
    private Boolean canGetChildren;
    private Boolean canMoveObject;
    private Boolean canApplyAcl;
    private Boolean canCheckOut;

    // Getter/Setter
    public Boolean getCanCreateDocument() {
        return canCreateDocument;
    }

    public void setCanCreateDocument(Boolean canCreateDocument) {
        this.canCreateDocument = canCreateDocument;
    }

    public Boolean getCanUpdateProperties() {
        return canUpdateProperties;
    }

    public void setCanUpdateProperties(Boolean canUpdateProperties) {
        this.canUpdateProperties = canUpdateProperties;
    }

    public Boolean getCanDeleteObject() {
        return canDeleteObject;
    }

    public void setCanDeleteObject(Boolean canDeleteObject) {
        this.canDeleteObject = canDeleteObject;
    }

    public Boolean getCanGetChildren() {
        return canGetChildren;
    }

    public void setCanGetChildren(Boolean canGetChildren) {
        this.canGetChildren = canGetChildren;
    }

    public Boolean getCanMoveObject() {
        return canMoveObject;
    }

    public void setCanMoveObject(Boolean canMoveObject) {
        this.canMoveObject = canMoveObject;
    }

    public Boolean getCanApplyAcl() {
        return canApplyAcl;
    }

    public void setCanApplyAcl(Boolean canApplyAcl) {
        this.canApplyAcl = canApplyAcl;
    }

    public Boolean getCanCheckOut() {
        return canCheckOut;
    }

    public void setCanCheckOut(Boolean canCheckOut) {
        this.canCheckOut = canCheckOut;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
