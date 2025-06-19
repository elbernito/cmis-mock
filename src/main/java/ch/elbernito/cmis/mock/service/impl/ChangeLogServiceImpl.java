package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;
import ch.elbernito.cmis.mock.mapping.ChangeLogMapper;
import ch.elbernito.cmis.mock.model.ChangeLogModel;
import ch.elbernito.cmis.mock.repository.ChangeLogRepository;
import ch.elbernito.cmis.mock.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ChangeLogService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeLogServiceImpl implements ChangeLogService {

    private final ChangeLogRepository changeLogRepository;
    private final ChangeLogMapper changeLogMapper;

    @Override
    public List<ChangeLogDto> getAllEntries() {
        log.info("Fetching all changelog entries");
        List<ChangeLogModel> list = changeLogRepository.findAll();
        List<ChangeLogDto> result = new ArrayList<>();
        for (ChangeLogModel model : list) {
            result.add(changeLogMapper.toDto(model));
        }
        return result;
    }

    @Override
    public List<ChangeLogDto> getEntriesByObjectId(String objectId) {
        log.info("Fetching changelog entries for object: {}", objectId);
        List<ChangeLogModel> list = changeLogRepository.findAllByObjectId(objectId);
        List<ChangeLogDto> result = new ArrayList<>();
        for (ChangeLogModel model : list) {
            result.add(changeLogMapper.toDto(model));
        }
        return result;
    }

    @Override
    public ChangeLogDto addEntry(ChangeLogDto changeLogDto) {
        log.info("Adding changelog entry: type={} object={}", changeLogDto.getChangeType(), changeLogDto.getObjectId());
        ChangeLogModel entity = changeLogMapper.toEntity(changeLogDto);
        ChangeLogModel saved = changeLogRepository.save(entity);
        return changeLogMapper.toDto(saved);
    }
}
