package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;

import java.util.List;

/**
 * Service interface for ChangeLog management.
 */
public interface ChangeLogService {

    List<ChangeLogDto> getAllEntries();

    List<ChangeLogDto> getEntriesByObjectId(String objectId);

    ChangeLogDto addEntry(ChangeLogDto changeLogDto);
}
