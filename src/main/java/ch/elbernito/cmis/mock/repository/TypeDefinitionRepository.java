package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.TypeDefinitionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TypeDefinitionRepository extends JpaRepository<TypeDefinitionModel, Long> {
    Optional<TypeDefinitionModel> findByTypeId(String typeId);
}
