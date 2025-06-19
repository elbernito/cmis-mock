package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.VersionDto;
import ch.elbernito.cmis.mock.exception.VersionNotFoundException;
import ch.elbernito.cmis.mock.mapping.VersionMapper;
import ch.elbernito.cmis.mock.model.VersionModel;
import ch.elbernito.cmis.mock.repository.VersionRepository;
import ch.elbernito.cmis.mock.service.VersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of VersionService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VersionServiceImpl implements VersionService {

    private final VersionRepository versionRepository;
    private final VersionMapper versionMapper;

    @Override
    public VersionDto getVersion(String versionId) {
        log.info("Fetching version by ID: {}", versionId);
        VersionModel model = versionRepository.findByVersionId(versionId)
                .orElseThrow(() -> new VersionNotFoundException("Version not found: " + versionId));
        return versionMapper.toDto(model);
    }

    @Override
    public List<VersionDto> getVersionsForDocument(String objectId) {
        log.info("Fetching all versions for document objectId: {}", objectId);
        List<VersionModel> list = versionRepository.findAllByObjectId(objectId);
        List<VersionDto> result = new ArrayList<>();
        for (VersionModel model : list) {
            result.add(versionMapper.toDto(model));
        }
        return result;
    }

    @Override
    public VersionDto getLatestVersionForDocument(String objectId) {
        log.info("Fetching latest version for document objectId: {}", objectId);
        VersionModel model = versionRepository.findFirstByObjectIdAndIsLatestVersionTrue(objectId)
                .orElseThrow(() -> new VersionNotFoundException("Latest version not found for: " + objectId));
        return versionMapper.toDto(model);
    }
}
