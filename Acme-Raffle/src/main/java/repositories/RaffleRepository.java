
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Raffle;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Integer> {

	@Query("select distinct a from Raffle a join a.participations b join b.user c where c.id=?1 ")
	List<Raffle> raffleByParticpation(int id);

	@Query("select distinct a from Raffle a where a.manager.id=?1 ")
	List<Raffle> findByManager(int id);
	@Query("select a from Raffle a join a.comments c where c.id = ?1")
	Raffle findOneByComment(int commentId);
	@Query("select avg(a.rating/b.comments.size) from Raffle b join b.comments a where b.id=?1")
	Double avgStarCommentsRaffle(int id);

}
