
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

}
