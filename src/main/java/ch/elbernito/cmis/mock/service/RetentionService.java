package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.RetentionDto;

import java.util.List;

/**
 * Service interface for Retention management.
 */
public interface RetentionService {

    RetentionDto createRetention(RetentionDto retentionDto);

    RetentionDto getRetention(String retentionId);

    void deleteRetention(String retentionId);

    List<RetentionDto> getRetentionsByObjectId(String objectId);
}
