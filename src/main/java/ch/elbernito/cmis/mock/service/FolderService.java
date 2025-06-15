package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.FolderDto;
import org.springframework.data.domain.Page;

public interface FolderService {
    FolderDto createFolder(FolderDto dto);
    Page<FolderDto> listFolders(int page, int size);
    FolderDto getFolder(String id);
    FolderDto updateFolder(String id, FolderDto dto);
    void deleteFolder(String id);
}
