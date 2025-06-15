package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.VersionDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface VersioningService {
    VersionDto createVersion(VersionDto dto);
    Page<VersionDto> listVersions(int page, int size);
    VersionDto getVersion(String id);
    VersionDto getLatestVersion(String objectId);
    VersionDto updateVersion(String id, VersionDto dto);
    void deleteVersion(String id);
}
