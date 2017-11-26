
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import repositories.ActorRepository;

@Service
@Transactional
public class ActorService {

	//Manager repositories

	@Autowired
	private ActorRepository actorRepository;


	//Constructor

	public ActorService() {
		super();
	}

	public Actor save(final Actor actor) {
		// TODO Auto-generated method stub
		Assert.notNull(actor);
		return this.actorRepository.save(actor);

	}

	public Collection<Actor> findAll() {
		// TODO Auto-generated method stub
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int actorId) {
		// TODO Auto-generated method stub
		Assert.notNull(actorId);
		return this.actorRepository.findOne(actorId);
	}

	public Double avgStarCommentsActor(int id) {
		return actorRepository.avgStarCommentsActor(id);
	}

	//CRUD Methods

}
