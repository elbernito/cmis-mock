package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.AclDto;

import java.util.List;

/**
 * Service interface for ACL management.
 */
public interface AclService {

    List<AclDto> getAclForObject(String objectId);

    AclDto setAclForObject(String objectId, AclDto aclDto);

    AclDto updateAcl(String aclId, AclDto aclDto);

    void deleteAcl(String aclId);
}
