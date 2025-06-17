package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.dto.FolderTreeDto;

import java.util.List;

/**
 * Service interface for CMIS Folder operations.
 */
public interface FolderService {
    FolderDto getFolderById(Long id);

    FolderDto getFolderByObjectId(String objectId);

    List<FolderDto> getAllFolders();

    List<FolderDto> getFoldersByParentId(Long parentFolderId);

    FolderDto createFolder(FolderDto dto);

    FolderDto updateFolder(Long id, FolderDto dto);

    void deleteFolder(Long id);

    void deleteAllFolders();

    List<Object> getChildren(Long folderId);

    FolderTreeDto getFolderTree(Long folderId, int depth);

    FolderDto getParent(Long folderId);

    void deleteTree(Long folderId);

    List<DocumentDto> getCheckedOutDocs(Long folderId);
}
