package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import ch.elbernito.cmis.mock.exception.CmisNotFoundException;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.RelationshipModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.RelationshipRepository;
import ch.elbernito.cmis.mock.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RelationshipService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RelationshipServiceImpl implements RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final DocumentRepository documentRepository;

    @Override
    public RelationshipDto getRelationshipById(Long id) {
        log.info("Fetching relationship by id={}", id);
        RelationshipModel model = relationshipRepository.findById(id)
                .orElseThrow(() -> new CmisNotFoundException("Relationship not found for id=" + id));
        return toDto(model);
    }

    @Override
    public List<RelationshipDto> getAllRelationships() {
        log.info("Fetching all relationships");
        List<RelationshipDto> result = new ArrayList<>();
        for (RelationshipModel model : relationshipRepository.findAll()) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public List<RelationshipDto> getBySourceDocumentId(Long sourceId) {
        log.info("Fetching relationships by sourceDocumentId={}", sourceId);
        List<RelationshipDto> result = new ArrayList<>();
        for (RelationshipModel model : relationshipRepository.findBySourceDocument_Id(sourceId)) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public List<RelationshipDto> getByTargetDocumentId(Long targetId) {
        log.info("Fetching relationships by targetDocumentId={}", targetId);
        List<RelationshipDto> result = new ArrayList<>();
        for (RelationshipModel model : relationshipRepository.findByTargetDocument_Id(targetId)) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public RelationshipDto createRelationship(RelationshipDto dto) {
        log.info("Creating relationship: {}", dto);
        RelationshipModel model = new RelationshipModel();
        model.setType(dto.getType());
        model.setCreatedBy(dto.getCreatedBy());
        model.setCreatedAt(dto.getCreatedAt());

        DocumentModel source = documentRepository.findById(dto.getSourceDocumentId())
                .orElseThrow(() -> new CmisNotFoundException("Source document not found for id=" + dto.getSourceDocumentId()));
        DocumentModel target = documentRepository.findById(dto.getTargetDocumentId())
                .orElseThrow(() -> new CmisNotFoundException("Target document not found for id=" + dto.getTargetDocumentId()));

        model.setSourceDocument(source);
        model.setTargetDocument(target);

        RelationshipModel saved = relationshipRepository.save(model);
        return toDto(saved);
    }

    @Override
    public RelationshipDto updateRelationship(Long id, RelationshipDto dto) {
        log.info("Updating relationship id={}: {}", id, dto);
        RelationshipModel model = relationshipRepository.findById(id)
                .orElseThrow(() -> new CmisNotFoundException("Relationship not found for id=" + id));
        model.setType(dto.getType());
        model.setCreatedBy(dto.getCreatedBy());
        model.setCreatedAt(dto.getCreatedAt());

        DocumentModel source = documentRepository.findById(dto.getSourceDocumentId())
                .orElseThrow(() -> new CmisNotFoundException("Source document not found for id=" + dto.getSourceDocumentId()));
        DocumentModel target = documentRepository.findById(dto.getTargetDocumentId())
                .orElseThrow(() -> new CmisNotFoundException("Target document not found for id=" + dto.getTargetDocumentId()));

        model.setSourceDocument(source);
        model.setTargetDocument(target);

        RelationshipModel saved = relationshipRepository.save(model);
        return toDto(saved);
    }

    @Override
    public void deleteRelationship(Long id) {
        log.info("Deleting relationship id={}", id);
        if (!relationshipRepository.existsById(id)) {
            throw new CmisNotFoundException("Relationship not found for id=" + id);
        }
        relationshipRepository.deleteById(id);
    }

    @Override
    public void deleteAllRelationships() {
        log.info("Deleting all relationships");
        relationshipRepository.deleteAll();
    }

    private RelationshipDto toDto(RelationshipModel model) {
        return RelationshipDto.builder()
                .id(model.getId())
                .sourceDocumentId(model.getSourceDocument() != null ? model.getSourceDocument().getId() : null)
                .targetDocumentId(model.getTargetDocument() != null ? model.getTargetDocument().getId() : null)
                .type(model.getType())
                .createdBy(model.getCreatedBy())
                .createdAt(model.getCreatedAt())
                .build();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
