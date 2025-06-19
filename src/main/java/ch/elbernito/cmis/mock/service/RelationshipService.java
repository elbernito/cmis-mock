package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.RelationshipDto;

import java.util.List;

/**
 * Service interface for Relationship management.
 */
public interface RelationshipService {

    RelationshipDto createRelationship(RelationshipDto relationshipDto);

    RelationshipDto getRelationship(String relationshipId);

    void deleteRelationship(String relationshipId);

    List<RelationshipDto> getRelationshipsByObjectId(String objectId);
}
