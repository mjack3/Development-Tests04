
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Participation;
import repositories.ParticipationRepository;

@Service
@Transactional
public class ParticipationService {

	@Autowired
	private ParticipationRepository participationRepository;


	public ParticipationService() {
		super();
	}

	public Participation create() {
		Participation participation = new Participation();
		participation.setMoment(new Date());
		participation.setUsedCode(new String());
		return participation;
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return participationRepository.exists(id);
	}

	public List<Participation> findAll() {
		return participationRepository.findAll();
	}

	public Participation save(Participation entity) {
		Assert.notNull(entity);

		Participation aux = new Participation();

		if (participationRepository.exists(entity.getId())) {
			aux = participationRepository.findOne(entity.getId());
			aux.setMoment(new Date());
			aux.setUsedCode(entity.getUsedCode());
			participationRepository.save(aux);

		} else {

			aux = participationRepository.save(entity);

		}

		return aux;
	}

	public Participation findOne(Integer id) {
		Assert.notNull(id);
		return participationRepository.findOne(id);
	}

}
