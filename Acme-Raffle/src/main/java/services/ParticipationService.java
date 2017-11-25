
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ParticipationRepository;
import domain.Participation;

@Service
@Transactional
public class ParticipationService {

	@Autowired
	private ParticipationRepository	participationRepository;


	public ParticipationService() {
		super();
	}

	public Participation create() {
		final Participation participation = new Participation();
		participation.setMoment(new Date());
		participation.setUsedCode(new String());
		return participation;
	}

	public boolean exists(final Integer id) {
		Assert.notNull(id);
		return this.participationRepository.exists(id);
	}

	public List<Participation> findAll() {
		return this.participationRepository.findAll();
	}

	public Participation save(final Participation entity) {
		Assert.notNull(entity);

		Participation aux = new Participation();

		if (this.participationRepository.exists(entity.getId())) {
			aux = this.participationRepository.findOne(entity.getId());
			aux.setMoment(new Date());
			aux.setUsedCode(entity.getUsedCode());
			this.participationRepository.save(aux);

		} else
			aux = this.participationRepository.save(entity);

		return aux;
	}

	public Participation findOne(final Integer id) {
		Assert.notNull(id);
		return this.participationRepository.findOne(id);
	}

	public void delete(final Participation participation) {
		Assert.notNull(participation);
		Assert.isTrue(this.participationRepository.exists(participation.getId()));

		this.participationRepository.delete(participation);
	}

}
