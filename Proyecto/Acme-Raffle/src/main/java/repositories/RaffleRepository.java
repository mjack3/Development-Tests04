
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Raffle;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Integer> {

}
