
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auditor;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor, Integer> {

	/**
	 * Devuelve a un auditor por el id de la cuenta de usuario
	 * 
	 * @param id
	 * @return auditor
	 */
	@Query("select m from Auditor m where m.userAccount.id = ?1")
	Auditor findOneUserAccount(int id);

	@Query("select a from Auditor a join a.comments c where c.id = ?1")
	Auditor findOneByComment(int commentId);
}
