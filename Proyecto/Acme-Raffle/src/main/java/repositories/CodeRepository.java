
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Code;
import domain.Participation;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {

	//Codigos de dicha rifa
	@Query("select a.codes from Raffle a where a.id=?1")
	List<Code> codeByRaffle(int id);

	//Participaciones de dicha rifa
	@Query("select a.participations from Raffle a where a.id=?1")
	List<Participation> codeByParticipation(int id);

}
