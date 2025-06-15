package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.MetadataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Metadata entities.
 */
@Repository
public interface MetadataRepository extends JpaRepository<MetadataModel, String> {
}
