package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;
import ch.elbernito.cmis.mock.model.ChangeType;

import java.util.List;

/**
 * Service interface for ChangeLog management.
 */
public interface ChangeLogService {

    List<ChangeLogDto> getAllEntries();

    List<ChangeLogDto> getEntriesByObjectId(String objectId);

    ChangeLogDto addEntry(ChangeLogDto changeLogDto);

    /**
     * Creates a ChangeLog entry for the specified object and action.
     *
     * @param objectId   affected object ID
     * @param changeType type of change (CREATED, UPDATED, etc.)
     * @param summary    human readable summary (nullable)
     */
    void logChange(String objectId, ChangeType changeType, String summary);
}
