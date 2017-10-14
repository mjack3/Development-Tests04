
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Code;
import domain.Manager;
import domain.Raffle;
import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ManagerService {

	//Manager repositories

	@Autowired
	private ManagerRepository	managerRepository;
	@Autowired
	private LoginService		loginService;


	//Constructor

	public ManagerService() {
		super();
	}

	//CRUD Methods

	public Manager create() {
		final Manager manager = new Manager();

		manager.setName(new String());
		manager.setSurname(new String());
		manager.setEmail(new String());
		manager.setPhone(new String());
		manager.setPostal(new String());
		manager.setCity(new String());
		manager.setCountry(new String());

		final Authority auth = new Authority();
		auth.setAuthority("MANAGER");
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(auth));
		account.setUsername(new String());
		account.setPassword(new String());
		account.setBanned(false);

		manager.setUserAccount(account);
		manager.setRaffles(new ArrayList<Raffle>());

		return manager;
	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager);
		Manager m = null;

		if (this.exists(manager.getId())) {
			m = this.findOne(manager.getId());
			m.setName(manager.getName());
			m.setCity(manager.getCity());
			m.setCountry(manager.getCountry());
			m.setEmail(manager.getEmail());
			m.setPhone(manager.getPhone());
			m.setPostal(manager.getPostal());
			m.setSurname(manager.getSurname());

			m = this.managerRepository.save(m);
		} else {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			manager.getUserAccount().setPassword(encoder.encodePassword(manager.getUserAccount().getPassword(), null));
			m = this.managerRepository.save(manager);
		}
		return m;
	}

	public List<Manager> findAll() {
		return this.managerRepository.findAll();
	}

	public Manager findOne(final Integer arg0) {
		Assert.notNull(arg0);
		return this.managerRepository.findOne(arg0);
	}

	public boolean exists(final Integer arg0) {
		Assert.notNull(arg0);
		return this.managerRepository.exists(arg0);
	}
	/**
	 * Devuelve al manager logueado
	 *
	 * @return manager
	 */
	public Manager findPrincipal() {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.hasRole("MANAGER"));
		final Manager manager = this.managerRepository.findOneUserAccount(LoginService.getPrincipal().getId());
		return manager;
	}

	public Collection<Code> getCodes(final Integer totalCodes, final Integer numWinner) {
		// TODO Auto-generated method stub
		return null;
	}

}
