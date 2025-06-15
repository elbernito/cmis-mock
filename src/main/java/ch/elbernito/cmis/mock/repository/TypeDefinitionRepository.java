package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.TypeDefinitionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for TypeDefinition entities.
 */
@Repository
public interface TypeDefinitionRepository extends JpaRepository<TypeDefinitionModel, String> {
}
