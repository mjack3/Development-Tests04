
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RaffleRepository;
import security.LoginService;
import domain.Raffle;

@Service
@Transactional
public class RaffleService {

	@Autowired
	private RaffleRepository	raffleRepository;
	@Autowired
	private LoginService		loginService;


	public Raffle save(final Raffle raffle) {
		// TODO Auto-generated method stub
		Assert.notNull(raffle);
		Assert.isTrue(LoginService.hasRole("MANAGER"));
		Raffle saved = null;
		if (raffle.getId() == 0)	// alta nueva
			saved = this.raffleRepository.save(raffle);

		return saved;
	}

}
