package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.RelationshipDto;

import java.util.List;

/**
 * Service interface for CMIS Relationship operations.
 */
public interface RelationshipService {
    RelationshipDto getRelationshipById(Long id);

    List<RelationshipDto> getAllRelationships();

    List<RelationshipDto> getBySourceDocumentId(Long sourceId);

    List<RelationshipDto> getByTargetDocumentId(Long targetId);

    RelationshipDto createRelationship(RelationshipDto dto);

    RelationshipDto updateRelationship(Long id, RelationshipDto dto);

    void deleteRelationship(Long id);

    void deleteAllRelationships();
}
