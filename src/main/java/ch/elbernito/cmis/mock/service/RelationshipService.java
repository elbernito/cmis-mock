package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RelationshipService {
    RelationshipDto createRelationship(RelationshipDto dto);
    List<RelationshipDto> listRelationships(String sourceId);
    RelationshipDto getRelationship(String id);
    RelationshipDto updateRelationship(String id, RelationshipDto dto);
    void deleteRelationship(String id);
}
