package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;
import ch.elbernito.cmis.mock.model.ChangeLogModel;
import ch.elbernito.cmis.mock.repository.ChangeLogRepository;
import ch.elbernito.cmis.mock.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeLogServiceImpl implements ChangeLogService {

    private final Logger log = LoggerFactory.getLogger(ChangeLogServiceImpl.class);
    private final ChangeLogRepository changeLogRepository;

    @Override
    public ChangeLogDto createChangeLog(ChangeLogDto dto) {
        log.info("Creating ChangeLog: {}", dto);
        ChangeLogModel model = new ChangeLogModel();
        model.setObjectId(dto.getObjectId());
        model.setChangeType(dto.getChangeType());
        model.setChangedBy(dto.getChangedBy());
        model.setChangeTime(dto.getChangeTime());
        model = changeLogRepository.save(model);
        dto.setId(model.getId());
        return dto;
    }

    @Override
    public List<ChangeLogDto> getAllChangeLogs() {
        List<ChangeLogDto> result = new ArrayList<ChangeLogDto>();
        for (ChangeLogModel model : changeLogRepository.findAll()) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public List<ChangeLogDto> getChangeLogsByObjectId(String objectId) {
        List<ChangeLogDto> result = new ArrayList<ChangeLogDto>();
        for (ChangeLogModel model : changeLogRepository.findByObjectId(objectId)) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public void deleteChangeLog(Long id) {
        changeLogRepository.deleteById(id);
    }

    private ChangeLogDto toDto(ChangeLogModel model) {
        ChangeLogDto dto = new ChangeLogDto();
        dto.setId(model.getId());
        dto.setObjectId(model.getObjectId());
        dto.setChangeType(model.getChangeType());
        dto.setChangedBy(model.getChangedBy());
        dto.setChangeTime(model.getChangeTime());
        return dto;
    }
}
