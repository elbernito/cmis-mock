package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.exception.CmisNotFoundException;
import ch.elbernito.cmis.mock.model.FolderModel;
import ch.elbernito.cmis.mock.repository.FolderRepository;
import ch.elbernito.cmis.mock.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
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

    @Override
    public FolderDto getFolderById(Long id) {
        log.info("Fetching folder by id={}", id);
        FolderModel model = folderRepository.findById(id)
                .orElseThrow(() -> new CmisNotFoundException("Folder not found for id=" + id));
        return toDto(model);
    }

    @Override
    public FolderDto getFolderByObjectId(String objectId) {
        log.info("Fetching folder by objectId={}", objectId);
        FolderModel model = folderRepository.findByObjectId(objectId);
        if (model == null) {
            throw new CmisNotFoundException("Folder not found for objectId=" + objectId);
        }
        return toDto(model);
    }

    @Override
    public List<FolderDto> getAllFolders() {
        log.info("Fetching all folders");
        List<FolderDto> result = new ArrayList<>();
        for (FolderModel model : folderRepository.findAll()) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public List<FolderDto> getFoldersByParentId(Long parentFolderId) {
        log.info("Fetching folders by parentFolderId={}", parentFolderId);
        List<FolderDto> result = new ArrayList<>();
        for (FolderModel model : folderRepository.findByParentFolder_Id(parentFolderId)) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public FolderDto createFolder(FolderDto dto) {
        log.info("Creating folder: {}", dto);
        FolderModel model = new FolderModel();
        model.setObjectId(dto.getObjectId());
        model.setName(dto.getName());
        model.setTypeId(dto.getTypeId());
        model.setCreationDate(dto.getCreationDate());
        model.setCreatedBy(dto.getCreatedBy());
        model.setLastModifiedDate(dto.getLastModifiedDate());
        model.setLastModifiedBy(dto.getLastModifiedBy());
        if (dto.getParentFolderId() != null) {
            FolderModel parent = folderRepository.findById(dto.getParentFolderId())
                    .orElseThrow(() -> new CmisNotFoundException("Parent folder not found for id=" + dto.getParentFolderId()));
            model.setParentFolder(parent);
        }
        FolderModel saved = folderRepository.save(model);
        return toDto(saved);
    }

    @Override
    public FolderDto updateFolder(Long id, FolderDto dto) {
        log.info("Updating folder id={}: {}", id, dto);
        FolderModel model = folderRepository.findById(id)
                .orElseThrow(() -> new CmisNotFoundException("Folder not found for id=" + id));
        model.setObjectId(dto.getObjectId());
        model.setName(dto.getName());
        model.setTypeId(dto.getTypeId());
        model.setCreationDate(dto.getCreationDate());
        model.setCreatedBy(dto.getCreatedBy());
        model.setLastModifiedDate(dto.getLastModifiedDate());
        model.setLastModifiedBy(dto.getLastModifiedBy());
        if (dto.getParentFolderId() != null) {
            FolderModel parent = folderRepository.findById(dto.getParentFolderId())
                    .orElseThrow(() -> new CmisNotFoundException("Parent folder not found for id=" + dto.getParentFolderId()));
            model.setParentFolder(parent);
        } else {
            model.setParentFolder(null);
        }
        FolderModel saved = folderRepository.save(model);
        return toDto(saved);
    }

    @Override
    public void deleteFolder(Long id) {
        log.info("Deleting folder id={}", id);
        if (!folderRepository.existsById(id)) {
            throw new CmisNotFoundException("Folder not found for id=" + id);
        }
        folderRepository.deleteById(id);
    }

    @Override
    public void deleteAllFolders() {
        log.info("Deleting all folders");
        folderRepository.deleteAll();
    }

    private FolderDto toDto(FolderModel model) {
        Long parentId = (model.getParentFolder() != null) ? model.getParentFolder().getId() : null;
        return FolderDto.builder()
                .id(model.getId())
                .objectId(model.getObjectId())
                .name(model.getName())
                .typeId(model.getTypeId())
                .creationDate(model.getCreationDate())
                .createdBy(model.getCreatedBy())
                .lastModifiedDate(model.getLastModifiedDate())
                .lastModifiedBy(model.getLastModifiedBy())
                .parentFolderId(parentId)
                .build();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
