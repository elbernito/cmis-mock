package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.FolderDto;

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
}
