
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select avg(a.rating/b.comments.size) from Actor b join b.comments a where b.id=?1")
	Double avgStarCommentsActor(int id);

}
