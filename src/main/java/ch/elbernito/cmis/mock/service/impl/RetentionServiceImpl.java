package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import ch.elbernito.cmis.mock.exception.CmisNotFoundException;
import ch.elbernito.cmis.mock.model.RetentionModel;
import ch.elbernito.cmis.mock.repository.RetentionRepository;
import ch.elbernito.cmis.mock.service.RetentionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RetentionServiceImpl implements RetentionService {

    private static final Logger log = LoggerFactory.getLogger(RetentionServiceImpl.class);

    private final RetentionRepository retentionRepository;

    @Override
    public RetentionDto create(RetentionDto dto) {
        log.info("Creating retention: {}", dto);
        RetentionModel model = toModel(dto);
        RetentionModel saved = retentionRepository.save(model);
        return toDto(saved);
    }

    @Override
    public RetentionDto update(String retentionId, RetentionDto dto) {
        log.info("Updating retention {}: {}", retentionId, dto);
        RetentionModel existing = retentionRepository.findById(retentionId)
                .orElseThrow(() -> new CmisNotFoundException("Retention not found: " + retentionId));
        existing.setObjectId(dto.getObjectId());
        existing.setRetentionStart(dto.getRetentionStart());
        existing.setRetentionEnd(dto.getRetentionEnd());
        existing.setDescription(dto.getDescription());
        existing.setActive(dto.isActive());
        return toDto(retentionRepository.save(existing));
    }

    @Override
    public void delete(String retentionId) {
        log.info("Deleting retention: {}", retentionId);
        retentionRepository.deleteById(retentionId);
    }

    @Override
    public RetentionDto getById(String retentionId) {
        return retentionRepository.findById(retentionId).map(this::toDto)
                .orElseThrow(() -> new CmisNotFoundException("Retention not found: " + retentionId));
    }

    @Override
    public List<RetentionDto> getAll() {
        return retentionRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RetentionDto> getByObjectId(String objectId) {
        return retentionRepository.findByObjectId(objectId).stream().map(this::toDto).collect(Collectors.toList());
    }

    private RetentionModel toModel(RetentionDto dto) {
        return RetentionModel.builder()
                .retentionId(dto.getRetentionId())
                .objectId(dto.getObjectId())
                .retentionStart(dto.getRetentionStart())
                .retentionEnd(dto.getRetentionEnd())
                .description(dto.getDescription())
                .active(dto.isActive())
                .build();
    }

    private RetentionDto toDto(RetentionModel model) {
        return RetentionDto.builder()
                .retentionId(model.getRetentionId())
                .objectId(model.getObjectId())
                .retentionStart(model.getRetentionStart())
                .retentionEnd(model.getRetentionEnd())
                .description(model.getDescription())
                .active(model.isActive())
                .build();
    }
}
