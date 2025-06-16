package ch.elbernito.cmis.mock.cmis12.dto;

import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO for static repository capabilities.
 */
public class RepositoryCapabilitiesDto {
    private String aclCapability;
    private String changesCapability;
    private Boolean multifiling;
    private Boolean unfiling;
    private Boolean versionSpecificFiling;
    private Boolean query;
    private String join;
    private List<String> datatypes;

    public String getAclCapability() { return aclCapability; }
    public void setAclCapability(String aclCapability) { this.aclCapability = aclCapability; }
    public String getChangesCapability() { return changesCapability; }
    public void setChangesCapability(String changesCapability) { this.changesCapability = changesCapability; }
    public Boolean getMultifiling() { return multifiling; }
    public void setMultifiling(Boolean multifiling) { this.multifiling = multifiling; }
    public Boolean getUnfiling() { return unfiling; }
    public void setUnfiling(Boolean unfiling) { this.unfiling = unfiling; }
    public Boolean getVersionSpecificFiling() { return versionSpecificFiling; }
    public void setVersionSpecificFiling(Boolean versionSpecificFiling) { this.versionSpecificFiling = versionSpecificFiling; }
    public Boolean getQuery() { return query; }
    public void setQuery(Boolean query) { this.query = query; }
    public String getJoin() { return join; }
    public void setJoin(String join) { this.join = join; }
    public List<String> getDatatypes() { return datatypes; }
    public void setDatatypes(List<String> datatypes) { this.datatypes = datatypes; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
