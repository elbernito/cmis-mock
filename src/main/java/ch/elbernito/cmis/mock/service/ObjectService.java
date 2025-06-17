package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.ObjectDto;

import java.util.List;

public interface ObjectService {
    ObjectDto createObject(ObjectDto dto);
    ObjectDto getObjectById(Long id);
    ObjectDto getObjectByObjectId(String objectId);
    List<ObjectDto> getAllObjects();
    ObjectDto updateObject(Long id, ObjectDto dto);
    void deleteObject(Long id);
}
