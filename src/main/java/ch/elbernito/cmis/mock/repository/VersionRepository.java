package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.VersionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for VersionModel.
 */
@Repository
public interface VersionRepository extends JpaRepository<VersionModel, Long> {
    Optional<VersionModel> findByVersionId(String versionId);

    List<VersionModel> findAllByObjectId(String objectId);

    Optional<VersionModel> findFirstByObjectIdAndIsLatestVersionTrue(String objectId);
}
