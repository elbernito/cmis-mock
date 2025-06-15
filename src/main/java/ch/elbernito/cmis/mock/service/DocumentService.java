package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public interface DocumentService {
    DocumentDto createDocument(MultipartFile file);
    Page<DocumentDto> listDocuments(int page, int size);
    DocumentDto getDocument(String id);
    byte[] getContent(String id);
    DocumentDto updateDocument(String id, MultipartFile file);
    void deleteDocument(String id);
}
