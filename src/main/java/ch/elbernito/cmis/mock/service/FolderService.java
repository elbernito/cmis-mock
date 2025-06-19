package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.FolderDto;

import java.util.List;

/**
 * Service interface for Folder management.
 */
public interface FolderService {

    FolderDto createFolder(FolderDto folderDto);

    FolderDto getFolder(String folderId);

    FolderDto updateFolder(String folderId, FolderDto folderDto);

    void deleteFolder(String folderId);

    List<FolderDto> getChildren(String folderId);

    List<FolderDto> getDescendants(String folderId);

    FolderDto getParent(String folderId);

    void deleteTree(String folderId);

    List<String> getCheckedOutDocs(String folderId);
}
