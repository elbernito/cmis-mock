package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.RetentionDto;

import java.util.List;

public interface RetentionService {
    RetentionDto create(RetentionDto retentionDto);

    RetentionDto update(String retentionId, RetentionDto retentionDto);

    void delete(String retentionId);

    RetentionDto getById(String retentionId);

    List<RetentionDto> getAll();

    List<RetentionDto> getByObjectId(String objectId);
}
