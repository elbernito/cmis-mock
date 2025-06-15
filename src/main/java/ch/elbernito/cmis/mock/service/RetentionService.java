package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface RetentionService {
    RetentionDto createRetention(RetentionDto dto);
    Page<RetentionDto> listRetentions(int page, int size);
    RetentionDto getRetention(String id);
    RetentionDto updateRetention(String id, RetentionDto dto);
    void deleteRetention(String id);
}
