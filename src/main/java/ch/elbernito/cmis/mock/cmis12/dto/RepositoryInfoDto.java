package ch.elbernito.cmis.mock.cmis12.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO representing static CMIS 1.2 Repository Info.
 */
public class RepositoryInfoDto {

    private String id;
    private String name;
    private String description;
    private String vendorName;
    private String productName;
    private String productVersion;
    private String cmisVersionSupported;
    private String rootFolderId;
    private String principalIdAnonymous;
    private String principalIdAnyone;
    private CapabilitiesDto capabilities;
    private RepositorySpecificInformationDto repositorySpecificInformation;

    // --- Getter and Setter ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getCmisVersionSupported() {
        return cmisVersionSupported;
    }

    public void setCmisVersionSupported(String cmisVersionSupported) {
        this.cmisVersionSupported = cmisVersionSupported;
    }

    public String getRootFolderId() {
        return rootFolderId;
    }

    public void setRootFolderId(String rootFolderId) {
        this.rootFolderId = rootFolderId;
    }

    public String getPrincipalIdAnonymous() {
        return principalIdAnonymous;
    }

    public void setPrincipalIdAnonymous(String principalIdAnonymous) {
        this.principalIdAnonymous = principalIdAnonymous;
    }

    public String getPrincipalIdAnyone() {
        return principalIdAnyone;
    }

    public void setPrincipalIdAnyone(String principalIdAnyone) {
        this.principalIdAnyone = principalIdAnyone;
    }

    public CapabilitiesDto getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(CapabilitiesDto capabilities) {
        this.capabilities = capabilities;
    }

    public RepositorySpecificInformationDto getRepositorySpecificInformation() {
        return repositorySpecificInformation;
    }

    public void setRepositorySpecificInformation(RepositorySpecificInformationDto repositorySpecificInformation) {
        this.repositorySpecificInformation = repositorySpecificInformation;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
