package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.VersionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VersionRepository extends JpaRepository<VersionModel, Long> {
    List<VersionModel> findByDocumentId(Long documentId);
}
