package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.RelationshipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for RelationshipModel.
 */
@Repository
public interface RelationshipRepository extends JpaRepository<RelationshipModel, Long> {
    List<RelationshipModel> findBySourceDocument_Id(Long sourceId);
    List<RelationshipModel> findByTargetDocument_Id(Long targetId);
}
