
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RaffleRepository;
import domain.Participation;
import domain.Prize;
import domain.Raffle;

@Service
@Transactional
public class RaffleService {

	@Autowired
	private RaffleRepository		raffleRepository;

	@Autowired
	private PrizeService			prizeService;

	@Autowired
	private ParticipationService	participationService;


	public Raffle save(final Raffle raffle) {
		// TODO Auto-generated method stub
		Assert.notNull(raffle);
		//Assert.isTrue(LoginService.hasRole("MANAGER"));
		Raffle saved = null;
		saved = this.raffleRepository.save(raffle);

		return saved;
	}

	public List<Raffle> findAll() {
		return this.raffleRepository.findAll();
	}
	public Boolean exist(final int raffleId) {
		return this.raffleRepository.exists(raffleId);
	}

	public Raffle findOne(final int raffleId) {

		return this.raffleRepository.findOne(raffleId);
	}

	public Raffle findOne(final Integer id) {
		Assert.notNull(id);
		return this.raffleRepository.findOne(id);
	}

	public List<Raffle> raffleByParticpation(final int id) {
		Assert.notNull(id);
		return this.raffleRepository.raffleByParticpation(id);
	}

	public Boolean isEditable(final int raffleId) {
		final Raffle raffle = this.findOne(raffleId);
		final Date now = new Date();
		return raffle.getPublicationTime().after(now);//True if can be editable
	}

	public void delete(final Raffle raffle) {
		Assert.notNull(raffle);
		Assert.isTrue(this.raffleRepository.exists(raffle.getId()));
		for (final Prize p : raffle.getPrizes())
			this.prizeService.delete(p);
		for (final Participation p : raffle.getParticipations())
			this.participationService.delete(p);
		this.raffleRepository.delete(raffle);
	}

	public List<Raffle> findByManager(final int id) {
		return this.raffleRepository.findByManager(id);
	}
}
