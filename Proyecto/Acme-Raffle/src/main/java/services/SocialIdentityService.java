
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Manager;
import domain.SocialIdentity;
import domain.User;
import repositories.SocialIdentityRepository;
import security.Authority;
import security.LoginService;

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
		SocialIdentity socialIdentity = new SocialIdentity();
		socialIdentity.setNick(new String());
		socialIdentity.setUrl(new String());

		return socialIdentity;
	}

	public SocialIdentity save(SocialIdentity entity) {
		Assert.notNull(entity);

		SocialIdentity aux = new SocialIdentity();

		if (socialIdentityRepository.exists(entity.getId())) {
			aux = socialIdentityRepository.findOne(entity.getId());
			aux.setNick(entity.getNick());
			aux.setUrl(entity.getUrl());
			socialIdentityRepository.save(aux);

		} else {

			aux = socialIdentityRepository.save(entity);
			Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

			actor.getSocialIdentities().add(aux);
			if (actor.getUserAccount().getAuthorities().contains(Authority.ADMIN)) {
				administratorService.save((Administrator) actor);
			} else if (actor.getUserAccount().getAuthorities().contains(Authority.MANAGER)) {
				managerService.save((Manager) actor);
			} else if (actor.getUserAccount().getAuthorities().contains(Authority.USER)) {
				userService.save((User) actor);
			}

		}

		return socialIdentityRepository.save(entity);
	}

	public SocialIdentity findOne(Integer id) {
		Assert.notNull(id);
		return socialIdentityRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return socialIdentityRepository.exists(id);
	}

	public void delete(SocialIdentity entity) {
		Assert.notNull(entity);
		Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		actor.getSocialIdentities().remove(entity);
		if (actor.getUserAccount().getAuthorities().contains(Authority.ADMIN)) {
			administratorService.save((Administrator) actor);
		} else if (actor.getUserAccount().getAuthorities().contains(Authority.MANAGER)) {
			managerService.save((Manager) actor);
		} else if (actor.getUserAccount().getAuthorities().contains(Authority.USER)) {
			userService.save((User) actor);
		}

		socialIdentityRepository.delete(entity);
	}

	public List<SocialIdentity> findAll() {
		return socialIdentityRepository.findAll();
	}

	public void delete(Integer id) {
		socialIdentityRepository.delete(id);
	}

}
