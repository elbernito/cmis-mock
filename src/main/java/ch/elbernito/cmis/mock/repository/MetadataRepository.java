package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.MetadataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for MetadataModel.
 */
@Repository
public interface MetadataRepository extends JpaRepository<MetadataModel, Long> {
    List<MetadataModel> findByDocument_Id(Long documentId);
    List<MetadataModel> findByFolder_Id(Long folderId);
}
