package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;
import java.util.List;

public interface ChangeLogService {
    ChangeLogDto createChangeLog(ChangeLogDto dto);
    List<ChangeLogDto> getAllChangeLogs();
    List<ChangeLogDto> getChangeLogsByObjectId(String objectId);
    void deleteChangeLog(Long id);
}
