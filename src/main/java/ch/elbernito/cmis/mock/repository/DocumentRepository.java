package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.DocumentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for DocumentModel.
 */
@Repository
public interface DocumentRepository extends JpaRepository<DocumentModel, Long> {
    DocumentModel findByObjectId(String objectId);
    List<DocumentModel> findByCheckedOutTrue();
    List<DocumentModel> findByParentFolderId(Long parentFolderId);
    List<DocumentModel> findByParentFolderIdAndCheckedOutTrue(Long folderId);
}
