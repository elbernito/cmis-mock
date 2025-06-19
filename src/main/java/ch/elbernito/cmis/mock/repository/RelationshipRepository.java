package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.RelationshipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for RelationshipModel.
 */
@Repository
public interface RelationshipRepository extends JpaRepository<RelationshipModel, Long> {

    Optional<RelationshipModel> findByRelationshipId(String relationshipId);

    List<RelationshipModel> findAllBySourceId(String sourceId);

    List<RelationshipModel> findAllByTargetId(String targetId);
}
