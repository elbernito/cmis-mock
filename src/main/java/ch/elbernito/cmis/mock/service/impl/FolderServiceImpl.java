package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.exception.FolderNotFoundException;
import ch.elbernito.cmis.mock.mapping.FolderMapper;
import ch.elbernito.cmis.mock.model.FolderModel;
import ch.elbernito.cmis.mock.repository.FolderRepository;
import ch.elbernito.cmis.mock.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of FolderService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;

    @Override
    public FolderDto createFolder(FolderDto folderDto) {
        log.info("Creating folder: {}", folderDto.getName());
        FolderModel model = folderMapper.toEntity(folderDto);
        if (folderDto.getParentFolderId() != null) {
            FolderModel parent = folderRepository.findByFolderId(folderDto.getParentFolderId())
                    .orElseThrow(() -> new FolderNotFoundException("Parent folder not found: " + folderDto.getParentFolderId()));
            model.setParentFolder(parent);
        }
        FolderModel saved = folderRepository.save(model);
        return folderMapper.toDto(saved);
    }

    @Override
    public FolderDto getFolder(String folderId) {
        log.info("Fetching folder by ID: {}", folderId);
        FolderModel model = folderRepository.findByFolderId(folderId)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found: " + folderId));
        return folderMapper.toDto(model);
    }

    @Override
    public FolderDto updateFolder(String folderId, FolderDto folderDto) {
        log.info("Updating folder: {}", folderId);
        FolderModel existing = folderRepository.findByFolderId(folderId)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found: " + folderId));
        existing.setName(folderDto.getName());
        if (folderDto.getParentFolderId() != null) {
            FolderModel parent = folderRepository.findByFolderId(folderDto.getParentFolderId())
                    .orElseThrow(() -> new FolderNotFoundException("Parent folder not found: " + folderDto.getParentFolderId()));
            existing.setParentFolder(parent);
        }
        FolderModel saved = folderRepository.save(existing);
        return folderMapper.toDto(saved);
    }

    @Override
    public void deleteFolder(String folderId) {
        log.info("Deleting folder: {}", folderId);
        FolderModel model = folderRepository.findByFolderId(folderId)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found: " + folderId));
        folderRepository.delete(model);
    }

    @Override
    public List<FolderDto> getChildren(String folderId) {
        log.info("Fetching children of folder: {}", folderId);
        FolderModel parent = folderRepository.findByFolderId(folderId)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found: " + folderId));
        List<FolderModel> children = folderRepository.findAllByParentFolder(parent);
        List<FolderDto> dtos = new ArrayList<>();
        for (FolderModel model : children) {
            dtos.add(folderMapper.toDto(model));
        }
        return dtos;
    }

    public List<FolderDto> getDescendants(String folderId) {
        FolderModel root = folderRepository.findByFolderId(folderId).orElseThrow();
        List<FolderDto> descendants = new ArrayList<>();
        collectDescendants(root, descendants);
        return descendants;
    }

    private void collectDescendants(FolderModel folder, List<FolderDto> result) {
        List<FolderModel> children = folderRepository.findAllByParentFolder(folder);
        for (FolderModel child : children) {
            result.add(folderMapper.toDto(child));
            collectDescendants(child, result);
        }
    }

    @Override
    public FolderDto getParent(String folderId) {
        log.info("Fetching parent of folder: {}", folderId);
        FolderModel model = folderRepository.findByFolderId(folderId)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found: " + folderId));

        log.info("Main folder: {}", ReflectionToStringBuilder.toString(model, ToStringStyle.JSON_STYLE));


        if (model.getParentFolder() == null) {
            log.info("Parent folder not found: {}", folderId);
            return null;
        }

        FolderDto result = folderMapper.toDto(model.getParentFolder());
        log.info("Parent folder: {}", ReflectionToStringBuilder.toString(result, ToStringStyle.JSON_STYLE));


        return result;
    }

    @Override
    public void deleteTree(String folderId) {
        log.info("Deleting folder tree: {}", folderId);
        FolderModel root = folderRepository.findByFolderId(folderId)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found: " + folderId));
        deleteRecursively(root);
    }

    private void deleteRecursively(FolderModel folder) {
        List<FolderModel> children = folderRepository.findAllByParentFolder(folder);
        for (FolderModel child : children) {
            deleteRecursively(child);
        }
        folderRepository.delete(folder);
    }

    @Override
    public List<String> getCheckedOutDocs(String folderId) {
        // Dummy implementation, would check DocumentRepository for checked out docs in this folder.
        log.info("Fetching checked-out docs in folder: {}", folderId);
        return new ArrayList<>();
    }
}
