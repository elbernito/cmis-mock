package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.FolderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Folder entities.
 */
@Repository
public interface FolderRepository extends JpaRepository<FolderModel, String> {
}
