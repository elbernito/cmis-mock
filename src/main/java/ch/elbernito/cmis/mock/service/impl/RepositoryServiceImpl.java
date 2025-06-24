package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.RepositoryDto;
import ch.elbernito.cmis.mock.exception.RepositoryNotFoundException;
import ch.elbernito.cmis.mock.mapping.RepositoryMapper;
import ch.elbernito.cmis.mock.model.RepositoryModel;
import ch.elbernito.cmis.mock.repository.RepositoryRepository;
import ch.elbernito.cmis.mock.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RepositoryService.
 */
@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class RepositoryServiceImpl implements RepositoryService {

    private final RepositoryRepository repositoryRepository;
    private final RepositoryMapper repositoryMapper;

    @Override
    public List<RepositoryDto> getAllRepositories() {
        log.info("Fetching all repositories");
        List<RepositoryModel> models = repositoryRepository.findAll();
        List<RepositoryDto> dtoList = new ArrayList<>();
        for (RepositoryModel model : models) {
            dtoList.add(repositoryMapper.toDto(model));
        }
        return dtoList;
    }

    @Override
    public RepositoryDto getRepositoryById(String repositoryId) {
        log.info("Fetching repository by id: {}", repositoryId);
        RepositoryModel model = repositoryRepository.findByRepositoryId(repositoryId)
                .orElseThrow(() -> new RepositoryNotFoundException("Repository not found: " + repositoryId));
        return repositoryMapper.toDto(model);
    }

    @Override
    public String getRepositoryInfo(String repositoryId) {
        log.info("Fetching repository info for id: {}", repositoryId);
        RepositoryModel model = repositoryRepository.findByRepositoryId(repositoryId)
                .orElseThrow(() -> new RepositoryNotFoundException("Repository not found: " + repositoryId));
        return model.getCapabilities();
    }

    @Override
    public RepositoryDto createRepository(RepositoryDto repositoryDto) {
        log.info("Creating new repository: {}", repositoryDto.getName());
        RepositoryModel entity = repositoryMapper.toEntity(repositoryDto);
        RepositoryModel saved = repositoryRepository.save(entity);
        return repositoryMapper.toDto(saved);
    }

    @Override
    public void deleteRepository(String repositoryId) {
        log.info("Deleting repository with id: {}", repositoryId);
        RepositoryModel model = repositoryRepository.findByRepositoryId(repositoryId)
                .orElseThrow(() -> new RepositoryNotFoundException("Repository not found: " + repositoryId));
        repositoryRepository.delete(model);
    }
}
