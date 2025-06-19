package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.TypeDefinitionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for TypeDefinitionModel.
 */
@Repository
public interface TypeDefinitionRepository extends JpaRepository<TypeDefinitionModel, Long> {

    Optional<TypeDefinitionModel> findByTypeId(String typeId);
}
