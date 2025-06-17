package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.dto.FolderTreeDto;
import ch.elbernito.cmis.mock.exception.CmisNotFoundException;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.FolderModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.FolderRepository;
import ch.elbernito.cmis.mock.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of FolderService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final DocumentRepository documentRepository;

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
        for (FolderModel model : folderRepository.findByParentFolderId(parentFolderId)) {
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

    @Override
    public List<Object> getChildren(Long folderId) {
        List<FolderModel> subFolders = folderRepository.findByParentFolderId(folderId);
        List<DocumentModel> docs = documentRepository.findByParentFolderId(folderId);

        final List<FolderDto> folderDtos = new ArrayList<>();
        if (null != subFolders) {
            for (FolderModel subFolder : subFolders) {
                folderDtos.add(FolderServiceImpl.toDto(subFolder));
            }
        }

        final List<DocumentDto> docDtos = new ArrayList<>();
        if (null != docs) {
            for (DocumentModel doc : docs) {
                docDtos.add(DocumentServiceImpl.toDto(doc));
            }
        }

        List<Object> children = new ArrayList<>();
        children.addAll(folderDtos);
        children.addAll(docDtos);
        return children;
    }

    @Override
    public FolderTreeDto getFolderTree(Long folderId, int depth) {
        FolderModel folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        FolderTreeDto tree = new FolderTreeDto();
        tree.setFolder(toDto(folder));
        if (depth == 0) return tree;
        List<FolderModel> children = folderRepository.findByParentFolderId(folderId);
        List<FolderTreeDto> childTrees = new ArrayList<>();
        for (FolderModel child : children) {
            childTrees.add(getFolderTree(child.getId(), depth - 1));
        }
        tree.setChildren(childTrees);
        return tree;
    }

    @Override
    public FolderDto getParent(Long folderId) {
        FolderModel folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        if (folder.getParentFolder() == null) return null;
        FolderModel parent = folderRepository.findById(folder.getParentFolder().getId())
                .orElse(null);
        return parent != null ? toDto(parent) : null;
    }

    @Override
    public void deleteTree(Long folderId) {
        // 1. Lösche alle Unterordner rekursiv
        List<FolderModel> subFolders = folderRepository.findByParentFolderId(folderId);
        for (FolderModel child : subFolders) {
            deleteTree(child.getId());
        }
        // 2. Lösche alle Dokumente im Ordner
        List<DocumentModel> docs = documentRepository.findByParentFolderId(folderId);
        for (DocumentModel doc : docs) {
            documentRepository.delete(doc);
        }
        // 3. Lösche den Ordner selbst
        folderRepository.deleteById(folderId);
    }

    @Override
    public List<DocumentDto> getCheckedOutDocs(Long folderId) {
        List<DocumentModel> docs = documentRepository.findByParentFolderIdAndCheckedOutTrue(folderId);
        return docs.stream().map(DocumentServiceImpl::toDto).collect(Collectors.toList());
    }

    public static FolderDto toDto(FolderModel model) {
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
