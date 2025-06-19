package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.AclModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for AclModel.
 */
@Repository
public interface AclRepository extends JpaRepository<AclModel, Long> {

    Optional<AclModel> findByAclId(String aclId);

    List<AclModel> findAllByObjectId(String objectId);

    List<AclModel> findByObjectIdAndPrincipal(String objectId, String principal);

}
