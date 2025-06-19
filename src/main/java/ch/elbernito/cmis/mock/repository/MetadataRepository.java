package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.MetadataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for MetadataModel.
 */
@Repository
public interface MetadataRepository extends JpaRepository<MetadataModel, Long> {

    Optional<MetadataModel> findByMetadataId(String metadataId);

    List<MetadataModel> findAllByDocumentId(String documentId);
}
