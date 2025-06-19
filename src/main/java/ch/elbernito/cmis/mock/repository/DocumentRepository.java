package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.DocumentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for DocumentModel.
 */
@Repository
public interface DocumentRepository extends JpaRepository<DocumentModel, Long> {

    Optional<DocumentModel> findByDocumentId(String documentId);

    List<DocumentModel> findAllByParentFolderId(String parentFolderId);

    List<DocumentModel> findAllByObjectId(String objectId);

    List<DocumentModel> findAllByIsLatestVersion(Boolean isLatestVersion);
}
