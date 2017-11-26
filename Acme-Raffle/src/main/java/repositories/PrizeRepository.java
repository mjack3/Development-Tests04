
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Prize;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Integer> {

	@Query("select p from Raffle r join r.prizes p where r.id= ?1")
	List<Prize> findAllByRaffleId(int raffleId);

	@Query("select count(c) from Prize p join p.codes c where p.id=?1 and c.isWinner=true")
	int countWinnersCode(int prizeId);

	@Query("select a from Prize a join a.comments c where c.id = ?1")
	Prize findOneByComment(int commentId);

	@Query("select avg(a.rating/b.comments.size) from Prize b join b.comments a where b.id=?1")
	Double avgStarCommentsPrize(int id);

}
