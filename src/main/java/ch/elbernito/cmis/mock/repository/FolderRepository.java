package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.FolderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for FolderModel.
 */
@Repository
public interface FolderRepository extends JpaRepository<FolderModel, Long> {
    FolderModel findByObjectId(String objectId);

    List<FolderModel> findByParentFolderId(Long parentFolder_id);
}
