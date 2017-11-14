
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	//Manager repositories

	@Autowired
	private ActorRepository	actorRepository;


	//Constructor

	public ActorService() {
		super();
	}

	public Actor save(final Actor actor) {
		// TODO Auto-generated method stub
		Assert.notNull(actor);
		return this.actorRepository.save(actor);

	}

	//CRUD Methods

}
