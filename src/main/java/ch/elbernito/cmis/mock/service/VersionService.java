package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.VersionDto;
import java.util.List;

public interface VersionService {
    VersionDto createVersion(VersionDto versionDto);
    VersionDto getVersion(Long id);
    List<VersionDto> getVersionsByDocumentId(Long documentId);
    VersionDto updateVersion(Long id, VersionDto versionDto);
    void deleteVersion(Long id);
}
