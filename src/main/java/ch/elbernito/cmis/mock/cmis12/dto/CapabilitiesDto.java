package ch.elbernito.cmis.mock.cmis12.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

// --- Inner DTOs ---
public class CapabilitiesDto {
    private String acl;
    private Boolean allVersionsSearchable;
    private String changes;
    private String contentStreamUpdatability;
    private Boolean getDescendants;
    private Boolean getFolderTree;
    private Boolean multifiling;
    private Boolean unfiling;
    private Boolean versionSpecificFiling;
    private Boolean pwcSearchable;
    private Boolean pwcUpdatable;
    private Boolean query;
    private String join;
    private Boolean fullText;
    private List<String> datatypes;

    // Getter/Setter
    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public Boolean getAllVersionsSearchable() {
        return allVersionsSearchable;
    }

    public void setAllVersionsSearchable(Boolean allVersionsSearchable) {
        this.allVersionsSearchable = allVersionsSearchable;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getContentStreamUpdatability() {
        return contentStreamUpdatability;
    }

    public void setContentStreamUpdatability(String contentStreamUpdatability) {
        this.contentStreamUpdatability = contentStreamUpdatability;
    }

    public Boolean getGetDescendants() {
        return getDescendants;
    }

    public void setGetDescendants(Boolean getDescendants) {
        this.getDescendants = getDescendants;
    }

    public Boolean getGetFolderTree() {
        return getFolderTree;
    }

    public void setGetFolderTree(Boolean getFolderTree) {
        this.getFolderTree = getFolderTree;
    }

    public Boolean getMultifiling() {
        return multifiling;
    }

    public void setMultifiling(Boolean multifiling) {
        this.multifiling = multifiling;
    }

    public Boolean getUnfiling() {
        return unfiling;
    }

    public void setUnfiling(Boolean unfiling) {
        this.unfiling = unfiling;
    }

    public Boolean getVersionSpecificFiling() {
        return versionSpecificFiling;
    }

    public void setVersionSpecificFiling(Boolean versionSpecificFiling) {
        this.versionSpecificFiling = versionSpecificFiling;
    }

    public Boolean getPwcSearchable() {
        return pwcSearchable;
    }

    public void setPwcSearchable(Boolean pwcSearchable) {
        this.pwcSearchable = pwcSearchable;
    }

    public Boolean getPwcUpdatable() {
        return pwcUpdatable;
    }

    public void setPwcUpdatable(Boolean pwcUpdatable) {
        this.pwcUpdatable = pwcUpdatable;
    }

    public Boolean getQuery() {
        return query;
    }

    public void setQuery(Boolean query) {
        this.query = query;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public Boolean getFullText() {
        return fullText;
    }

    public void setFullText(Boolean fullText) {
        this.fullText = fullText;
    }

    public List<String> getDatatypes() {
        return datatypes;
    }

    public void setDatatypes(List<String> datatypes) {
        this.datatypes = datatypes;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
