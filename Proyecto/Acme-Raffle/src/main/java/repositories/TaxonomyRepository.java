
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Taxonomy;

@Repository
public interface TaxonomyRepository extends JpaRepository<Taxonomy, Integer> {

}
