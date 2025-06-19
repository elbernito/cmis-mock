package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.DocumentContentDto;
import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.model.DocumentModel;

import java.util.List;

/**
 * Service interface for Document management.
 */
public interface DocumentService {

    DocumentDto createDocument(DocumentDto documentDto);

    DocumentDto getDocument(String documentId);

    DocumentDto updateDocument(String documentId, DocumentDto documentDto);

    void deleteDocument(String documentId);

    List<DocumentDto> getDocumentVersions(String documentId);

    DocumentContentDto downloadContent(String documentId);

    DocumentDto uploadContent(String documentId, DocumentContentDto contentDto);

    DocumentDto checkin(String documentId);

    DocumentDto checkout(String documentId);

    List<DocumentDto> getDocumentsByParentFolderId(String folderId);

    List<DocumentDto> findAllByIsLatestVersion(Boolean isLatestVersion);

}
