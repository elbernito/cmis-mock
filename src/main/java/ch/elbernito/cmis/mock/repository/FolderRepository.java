package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.FolderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for FolderModel.
 */
@Repository
public interface FolderRepository extends JpaRepository<FolderModel, Long> {

    Optional<FolderModel> findByFolderId(String folderId);

    Optional<FolderModel> findByObjectId(String objectId);

    List<FolderModel> findAllByParentFolder(FolderModel parentFolder);
}
