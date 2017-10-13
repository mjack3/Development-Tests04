
package services;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RaffleRepository;
import domain.Raffle;

@Service
@Transactional
public class RaffleService {

	@Autowired
	private RaffleRepository raffleRepository;


	





	public Raffle save(final Raffle raffle) {
		// TODO Auto-generated method stub
		Assert.notNull(raffle);
		//Assert.isTrue(LoginService.hasRole("MANAGER"));
		Raffle saved = null;
		if (raffle.getId() == 0)	// alta nueva
			saved = this.raffleRepository.save(raffle);

		return saved;
	}
	
	public List<Raffle> findAll(){
		return this.raffleRepository.findAll();
	}
	public Boolean exist(int raffleId){
		return this.raffleRepository.exists(raffleId);
	}

	public Raffle findOne(int raffleId) {
		
		return this.raffleRepository.findOne(raffleId);
	}

	public Raffle findOne(Integer id) {
		Assert.notNull(id);
		return raffleRepository.findOne(id);
	}

	public List<Raffle> raffleByParticpation(int id) {
		Assert.notNull(id);
		return raffleRepository.raffleByParticpation(id);
	}

}
