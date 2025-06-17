package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.AclModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AclRepository extends JpaRepository<AclModel, Long> {
}
