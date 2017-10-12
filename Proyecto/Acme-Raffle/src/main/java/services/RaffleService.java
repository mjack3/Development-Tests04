
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Raffle;
import repositories.RaffleRepository;
import security.LoginService;

@Service
@Transactional
public class RaffleService {

	@Autowired
	private RaffleRepository raffleRepository;


	public List<Raffle> findAll() {
		return raffleRepository.findAll();
	}


	@Autowired
	private LoginService loginService;


	public Raffle save(final Raffle raffle) {
		// TODO Auto-generated method stub
		Assert.notNull(raffle);
		//Assert.isTrue(LoginService.hasRole("MANAGER"));
		Raffle saved = null;
		if (raffle.getId() == 0)	// alta nueva
			saved = this.raffleRepository.save(raffle);

		return saved;
	}

	public Raffle findOne(Integer id) {
		Assert.notNull(id);
		return raffleRepository.findOne(id);
	}

}
