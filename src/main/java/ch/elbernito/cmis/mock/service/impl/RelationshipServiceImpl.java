package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import ch.elbernito.cmis.mock.exception.RelationshipNotFoundException;
import ch.elbernito.cmis.mock.mapping.RelationshipMapper;
import ch.elbernito.cmis.mock.model.ChangeType;
import ch.elbernito.cmis.mock.model.RelationshipModel;
import ch.elbernito.cmis.mock.repository.RelationshipRepository;
import ch.elbernito.cmis.mock.service.ChangeLogService;
import ch.elbernito.cmis.mock.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RelationshipService.
 */
@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class RelationshipServiceImpl implements RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final RelationshipMapper relationshipMapper;

    private final ChangeLogService changeLogService;

    @Override
    public RelationshipDto createRelationship(RelationshipDto relationshipDto) {
        log.info("Creating relationship {} -> {} type={}", relationshipDto.getSourceId(), relationshipDto.getTargetId(), relationshipDto.getRelationshipType());
        RelationshipModel entity = relationshipMapper.toEntity(relationshipDto);
        RelationshipModel saved = relationshipRepository.save(entity);

        // ChangeLog
        changeLogService.logChange(
                saved.getRelationshipId(),
                ChangeType.CREATED,
                "Relationship created: " + saved.getSourceId() + " -> " + saved.getTargetId() + ", type=" + saved.getRelationshipType()
        );

        return relationshipMapper.toDto(saved);
    }

    @Override
    public RelationshipDto getRelationship(String relationshipId) {
        log.info("Fetching relationship by ID: {}", relationshipId);
        RelationshipModel model = relationshipRepository.findByRelationshipId(relationshipId)
                .orElseThrow(() -> new RelationshipNotFoundException("Relationship not found: " + relationshipId));
        return relationshipMapper.toDto(model);
    }

    @Override
    public void deleteRelationship(String relationshipId) {
        log.info("Deleting relationship: {}", relationshipId);
        RelationshipModel model = relationshipRepository.findByRelationshipId(relationshipId)
                .orElseThrow(() -> new RelationshipNotFoundException("Relationship not found: " + relationshipId));
        relationshipRepository.delete(model);

        // ChangeLog
        changeLogService.logChange(
                relationshipId,
                ChangeType.DELETED,
                "Relationship deleted: " + model.getSourceId() + " -> " + model.getTargetId() + ", type=" + model.getRelationshipType()
        );
    }

    @Override
    public List<RelationshipDto> getRelationshipsByObjectId(String objectId) {
        log.info("Fetching all relationships for object: {}", objectId);
        List<RelationshipModel> list = new ArrayList<>();
        list.addAll(relationshipRepository.findAllBySourceId(objectId));
        list.addAll(relationshipRepository.findAllByTargetId(objectId));
        List<RelationshipDto> result = new ArrayList<>();
        for (RelationshipModel model : list) {
            result.add(relationshipMapper.toDto(model));
        }
        return result;
    }
}
