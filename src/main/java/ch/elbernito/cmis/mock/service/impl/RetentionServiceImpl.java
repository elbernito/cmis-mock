package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import ch.elbernito.cmis.mock.exception.RetentionNotFoundException;
import ch.elbernito.cmis.mock.mapping.RetentionMapper;
import ch.elbernito.cmis.mock.model.ChangeType;
import ch.elbernito.cmis.mock.model.RetentionModel;
import ch.elbernito.cmis.mock.repository.RetentionRepository;
import ch.elbernito.cmis.mock.service.ChangeLogService;
import ch.elbernito.cmis.mock.service.RetentionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RetentionService.
 */
@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class RetentionServiceImpl implements RetentionService {

    private final RetentionRepository retentionRepository;
    private final RetentionMapper retentionMapper;

    private final ChangeLogService changeLogService;

    @Override
    public RetentionDto createRetention(RetentionDto retentionDto) {
        log.info("Creating retention for objectId: {}", retentionDto.getObjectId());
        RetentionModel entity = retentionMapper.toEntity(retentionDto);
        RetentionModel saved = retentionRepository.save(entity);

        // ChangeLog
        changeLogService.logChange(
                saved.getObjectId(),
                ChangeType.SECURITY,
                "Retention policy '" + (saved.getLabel() != null ? saved.getLabel() : "") + "' created"
        );

        return retentionMapper.toDto(saved);
    }

    @Override
    public RetentionDto getRetention(String retentionId) {
        log.info("Fetching retention by ID: {}", retentionId);
        RetentionModel model = retentionRepository.findByRetentionId(retentionId)
                .orElseThrow(() -> new RetentionNotFoundException("Retention not found: " + retentionId));
        return retentionMapper.toDto(model);
    }

    @Override
    public void deleteRetention(String retentionId) {
        log.info("Deleting retention: {}", retentionId);
        RetentionModel model = retentionRepository.findByRetentionId(retentionId)
                .orElseThrow(() -> new RetentionNotFoundException("Retention not found: " + retentionId));
        retentionRepository.delete(model);

        // ChangeLog
        changeLogService.logChange(
                model.getObjectId(),
                ChangeType.SECURITY,
                "Retention policy '" + (model.getLabel() != null ? model.getLabel() : "") + "' deleted"
        );
    }

    @Override
    public List<RetentionDto> getRetentionsByObjectId(String objectId) {
        log.info("Fetching retentions for objectId: {}", objectId);
        List<RetentionModel> list = retentionRepository.findAllByObjectId(objectId);
        List<RetentionDto> result = new ArrayList<>();
        for (RetentionModel model : list) {
            result.add(retentionMapper.toDto(model));
        }
        return result;
    }
}
