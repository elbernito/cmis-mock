package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.DocumentDto;

import java.util.List;

/**
 * Service interface for CMIS Document operations.
 */
public interface DocumentService {
    DocumentDto getDocumentById(Long id);

    DocumentDto getDocumentByObjectId(String objectId);

    List<DocumentDto> getAllDocuments();

    DocumentDto createDocument(DocumentDto dto);

    DocumentDto updateDocument(Long id, DocumentDto dto);

    void deleteDocument(Long id);

    void deleteAllDocuments();
}
