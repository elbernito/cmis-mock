package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import java.util.List;

public interface ObjectService {
    ObjectDto createObject(ObjectDto dto);
    List<ObjectDto> listObjects(String type);
    ObjectDto getObject(String id);
    ObjectDto updateObject(String id, ObjectDto dto);
    void deleteObject(String id);
}
