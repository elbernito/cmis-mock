package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.StatisticsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Statistics entities.
 */
@Repository
public interface StatisticsRepository extends JpaRepository<StatisticsModel, String> {
}
