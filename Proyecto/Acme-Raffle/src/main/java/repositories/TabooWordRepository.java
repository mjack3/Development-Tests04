package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.TabooWord;

@Repository
public interface TabooWordRepository extends JpaRepository<TabooWord, Integer>{

}
