
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Prize;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Integer> {

}
