package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.DocumentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Document entities.
 */
@Repository
public interface DocumentRepository extends JpaRepository<DocumentModel, String> {
}
