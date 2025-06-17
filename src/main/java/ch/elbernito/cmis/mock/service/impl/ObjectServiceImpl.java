package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import ch.elbernito.cmis.mock.model.ObjectModel;
import ch.elbernito.cmis.mock.repository.ObjectRepository;
import ch.elbernito.cmis.mock.service.ObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for CMIS Object operations.
 */
@Service
public class ObjectServiceImpl implements ObjectService {

    private static final Logger log = LoggerFactory.getLogger(ObjectServiceImpl.class);

    private final ObjectRepository objectRepository;

    public ObjectServiceImpl(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @Override
    public ObjectDto createObject(ObjectDto dto) {
        log.info("Creating new CMIS Object: {}", dto);
        ObjectModel model = toModel(dto);
        ObjectModel saved = objectRepository.save(model);
        return toDto(saved);
    }

    @Override
    public ObjectDto getObjectById(Long id) {
        log.info("Fetching CMIS Object by id: {}", id);
        Optional<ObjectModel> optional = objectRepository.findById(id);
        return optional.map(this::toDto).orElse(null);
    }

    @Override
    public ObjectDto getObjectByObjectId(String objectId) {
        log.info("Fetching CMIS Object by objectId: {}", objectId);
        Optional<ObjectModel> optional = objectRepository.findByObjectId(objectId);
        return optional.map(this::toDto).orElse(null);
    }

    @Override
    public List<ObjectDto> getAllObjects() {
        log.info("Fetching all CMIS Objects");
        return objectRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ObjectDto updateObject(Long id, ObjectDto dto) {
        log.info("Updating CMIS Object id {}: {}", id, dto);
        Optional<ObjectModel> optional = objectRepository.findById(id);
        if (optional.isPresent()) {
            ObjectModel existing = optional.get();
            existing.setName(dto.getName());
            existing.setLastModifiedBy(dto.getLastModifiedBy());
            existing.setLastModificationDate(dto.getLastModificationDate());
            existing.setTypeId(dto.getTypeId());
            // ... ggf. weitere Felder
            ObjectModel saved = objectRepository.save(existing);
            return toDto(saved);
        }
        return null;
    }

    @Override
    public void deleteObject(Long id) {
        log.info("Deleting CMIS Object by id: {}", id);
        objectRepository.deleteById(id);
    }

    // --- Mapping ohne extra Mapper-Klasse ---
    private ObjectModel toModel(ObjectDto dto) {
        ObjectModel model = new ObjectModel();
        model.setId(dto.getId());
        model.setObjectId(dto.getObjectId());
        model.setName(dto.getName());
        model.setCreatedBy(dto.getCreatedBy());
        model.setCreationDate(dto.getCreationDate());
        model.setLastModifiedBy(dto.getLastModifiedBy());
        model.setLastModificationDate(dto.getLastModificationDate());
        model.setTypeId(dto.getTypeId());
        return model;
    }

    private ObjectDto toDto(ObjectModel model) {
        ObjectDto dto = new ObjectDto();
        dto.setId(model.getId());
        dto.setObjectId(model.getObjectId());
        dto.setName(model.getName());
        dto.setCreatedBy(model.getCreatedBy());
        dto.setCreationDate(model.getCreationDate());
        dto.setLastModifiedBy(model.getLastModifiedBy());
        dto.setLastModificationDate(model.getLastModificationDate());
        dto.setTypeId(model.getTypeId());
        return dto;
    }
}
