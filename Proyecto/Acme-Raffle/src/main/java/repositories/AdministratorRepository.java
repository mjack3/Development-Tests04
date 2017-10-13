
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.User;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	
	/**
	 * Devuelve a un administrador por el id de la cuenta de usuario
	 * 
	 * @param id
	 * @return manager
	 */
	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findOneUserAccount(int id);
	
	@Query("select min(r.prizes.size),max(r.prizes.size),avg(r.prizes.size),stddev(r.prizes.size) from Raffle r")
	Object[] prizesPerRaffle();
	
	@Query("select min(p.codes.size),max(p.codes.size),avg(p.codes.size),stddev(p.codes.size) from Prize p")
	Object[] codesPerPrizes();
	
	@Query("select min(u.validCodes),max(u.validCodes),avg(u.validCodes),stddev(u.validCodes) from User u")
	Object[] validCodesPerUser();
	
	@Query("select u from User u where u.validCodes = (select max(a.validCodes) from User a)")
	List<User> userWithMoreValidCodes();
}
