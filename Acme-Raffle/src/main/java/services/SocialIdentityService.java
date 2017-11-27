
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Administrator;
import domain.Manager;
import domain.SocialIdentity;
import domain.User;

@Service
@Transactional
public class SocialIdentityService {

	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	@Autowired
	private LoginService				loginService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private UserService					userService;

	@Autowired
	private ManagerService				managerService;


	public SocialIdentityService() {
		super();
	}

	public SocialIdentity create() {
		final SocialIdentity socialIdentity = new SocialIdentity();
		socialIdentity.setNick(new String());
		socialIdentity.setUrl(new String());

		return socialIdentity;
	}

	public SocialIdentity save(final SocialIdentity entity) {
		Assert.notNull(entity);

		SocialIdentity aux = new SocialIdentity();

		if (this.socialIdentityRepository.exists(entity.getId())) {
			aux = this.socialIdentityRepository.findOne(entity.getId());
			aux.setNick(entity.getNick());
			aux.setUrl(entity.getUrl());
			aux = this.socialIdentityRepository.save(aux);

		} else {

			aux = this.socialIdentityRepository.save(entity);
			final Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

			actor.getSocialIdentities().add(aux);
			if (actor.getUserAccount().getAuthorities().contains(Authority.ADMIN))
				this.administratorService.save((Administrator) actor);
			else if (actor.getUserAccount().getAuthorities().contains(Authority.MANAGER))
				this.managerService.save((Manager) actor);
			else if (actor.getUserAccount().getAuthorities().contains(Authority.USER))
				this.userService.save((User) actor);

		}

		return aux;
	}

	public SocialIdentity findOne(final Integer id) {
		Assert.notNull(id);
		return this.socialIdentityRepository.findOne(id);
	}

	public boolean exists(final Integer id) {
		Assert.notNull(id);
		return this.socialIdentityRepository.exists(id);
	}

	public void delete(final SocialIdentity entity) {
		Assert.notNull(entity);
		final Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		actor.getSocialIdentities().remove(entity);
		if (actor.getUserAccount().getAuthorities().contains(Authority.ADMIN))
			this.administratorService.save((Administrator) actor);
		else if (actor.getUserAccount().getAuthorities().contains(Authority.MANAGER))
			this.managerService.save((Manager) actor);
		else if (actor.getUserAccount().getAuthorities().contains(Authority.USER))
			this.userService.save((User) actor);

		this.socialIdentityRepository.delete(entity);
	}

	public List<SocialIdentity> findAll() {
		return this.socialIdentityRepository.findAll();
	}

	public void delete(final Integer id) {
		this.socialIdentityRepository.delete(id);
	}

}
