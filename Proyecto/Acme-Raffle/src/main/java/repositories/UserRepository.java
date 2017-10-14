
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.banned = true")
	List<User> usersBanned();

	@Query("select u from User u where u.userAccount.banned = false")
	List<User> usersNotBanned();

	@Query("select u from User u where u.userAccount.id = ?1")
	User findActorByUsernameId(Integer username);
}
