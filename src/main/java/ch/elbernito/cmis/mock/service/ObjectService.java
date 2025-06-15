package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ObjectService {
    ObjectDto createObject(ObjectDto dto);
    List<ObjectDto> listObjects(String type);
    ObjectDto getObject(String id);
    ObjectDto updateObject(String id, ObjectDto dto);
    void deleteObject(String id);
}
