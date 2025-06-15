package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.RelationshipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Relationship entities.
 */
@Repository
public interface RelationshipRepository extends JpaRepository<RelationshipModel, String> {
}
