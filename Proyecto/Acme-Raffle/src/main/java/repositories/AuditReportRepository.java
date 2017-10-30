
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AuditReport;

@Repository
public interface AuditReportRepository extends JpaRepository<AuditReport, Integer> {

	/**
	 * Devuelve reports por ID de raffle
	 * 
	 * @param id
	 * @return
	 */

	@Query("select resul from AuditReport resul where resul.raffle.id = ?1")
	Collection<AuditReport> findAllByRaffle(int id);
	@Query("select resul from AuditReport resul where resul.raffle.id = ?1 and resul.finalMode = true")
	Collection<AuditReport> findAllByRaffleFinal(int id);

}
