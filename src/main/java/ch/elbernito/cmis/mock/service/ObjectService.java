package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.ObjectDto;

import java.util.List;

/**
 * Service interface for CMIS Object management.
 */
public interface ObjectService {

    ObjectDto getObjectById(String objectId);

    ObjectDto getObjectByPath(String path);

    ObjectDto moveObject(String objectId, String targetFolderId);

    ObjectDto copyObject(String objectId, String targetFolderId);

    List<String> getAllowableActions(String objectId);

    List<ObjectDto> getRelationships(String objectId);
}
