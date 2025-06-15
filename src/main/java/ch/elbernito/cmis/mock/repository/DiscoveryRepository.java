package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.DiscoveryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Discovery entities.
 */
@Repository
public interface DiscoveryRepository extends JpaRepository<DiscoveryModel, String> {
}
