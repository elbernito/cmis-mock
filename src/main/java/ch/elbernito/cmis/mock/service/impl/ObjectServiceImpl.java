package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import ch.elbernito.cmis.mock.exception.CmisObjectNotFoundException;
import ch.elbernito.cmis.mock.mapping.ObjectMapper;
import ch.elbernito.cmis.mock.model.ObjectModel;
import ch.elbernito.cmis.mock.repository.ObjectRepository;
import ch.elbernito.cmis.mock.service.ObjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CmisObjectService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ObjectServiceImpl implements ObjectService {

    private final ObjectRepository cmisObjectRepository;
    private final ObjectMapper cmisObjectMapper;

    @Override
    public ObjectDto getObjectById(String objectId) {
        log.info("Fetching CMIS object by ID: {}", objectId);
        ObjectModel model = cmisObjectRepository.findByObjectId(objectId)
                .orElseThrow(() -> new CmisObjectNotFoundException("CMIS object not found: " + objectId));
        return cmisObjectMapper.toDto(model);
    }

    @Override
    public ObjectDto getObjectByPath(String path) {
        log.info("Fetching CMIS object by path: {}", path);
        ObjectModel model = cmisObjectRepository.findByPath(path)
                .orElseThrow(() -> new CmisObjectNotFoundException("CMIS object not found by path: " + path));
        return cmisObjectMapper.toDto(model);
    }

    @Override
    public ObjectDto moveObject(String objectId, String targetFolderId) {
        log.info("Moving object {} to folder {}", objectId, targetFolderId);
        ObjectModel model = cmisObjectRepository.findByObjectId(objectId)
                .orElseThrow(() -> new CmisObjectNotFoundException("CMIS object not found: " + objectId));
        model.setParentFolderId(targetFolderId);
        cmisObjectRepository.save(model);
        return cmisObjectMapper.toDto(model);
    }

    @Override
    public ObjectDto copyObject(String objectId, String targetFolderId) {
        log.info("Copying object {} to folder {}", objectId, targetFolderId);
        ObjectModel original = cmisObjectRepository.findByObjectId(objectId)
                .orElseThrow(() -> new CmisObjectNotFoundException("CMIS object not found: " + objectId));
        ObjectModel copy = ObjectModel.builder()
                .name(original.getName() + " (Copy)")
                .type(original.getType())
                .parentFolderId(targetFolderId)
                .path(original.getPath() + "_copy")
                .build();
        cmisObjectRepository.save(copy);
        return cmisObjectMapper.toDto(copy);
    }

    @Override
    public List<String> getAllowableActions(String objectId) {
        log.info("Fetching allowable actions for object {}", objectId);
        List<String> actions = new ArrayList<>();
        actions.add("read");
        actions.add("write");
        actions.add("delete");
        return actions;
    }

    @Override
    public List<ObjectDto> getRelationships(String objectId) {
        log.info("Fetching relationships for object {}", objectId);
        // Dummy implementation, in echt würde das RelationshipRepository befragt
        return new ArrayList<>();
    }
}
