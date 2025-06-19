package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.VersionDto;

import java.util.List;

/**
 * Service interface for Version management.
 */
public interface VersionService {

    VersionDto getVersion(String versionId);

    List<VersionDto> getVersionsForDocument(String objectId);

    VersionDto getLatestVersionForDocument(String objectId);
}
