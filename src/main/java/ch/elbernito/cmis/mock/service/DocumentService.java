package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.dto.VersionDto;

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

    DocumentDto checkOut(Long documentId, String userId);

    DocumentDto cancelCheckOut(Long documentId, String userId);

    DocumentDto checkIn(Long documentId, String userId, byte[] newContent, String comment);

    List<VersionDto> getAllVersions(Long documentId);

    byte[] getContentStream(Long documentId);

    void setContentStream(Long documentId, byte[] content);
}
