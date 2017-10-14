
package services;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.User;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	//User repositories

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private UserService				userService;


	//Constructor

	public AdministratorService() {
		super();
	}

	//CRUD Methods

	public Administrator create() {
		final Administrator administrator = new Administrator();

		administrator.setName(new String());
		administrator.setSurname(new String());
		administrator.setEmail(new String());
		administrator.setPhone(new String());
		administrator.setPostal(new String());
		administrator.setCity(new String());
		administrator.setCountry(new String());

		final Authority auth = new Authority();
		auth.setAuthority("ADMIN");
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(auth));
		account.setUsername(new String());
		account.setPassword(new String());
		//account.setActivate(true);

		administrator.setUserAccount(account);
		//user.setPrizes(new ArrayList<Prize>());
		//user.setParticipations(new ArrayList<Participation>());

		return administrator;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		Administrator a = null;

		if (this.exists(administrator.getId())) {
			a = this.findOne(administrator.getId());
			a.setName(administrator.getName());
			a.setCity(administrator.getCity());
			a.setCountry(administrator.getCountry());
			a.setEmail(administrator.getEmail());
			a.setPhone(administrator.getPhone());
			a.setPostal(administrator.getPostal());
			a.setSurname(administrator.getSurname());
			//user.setPrizes(administrator.getPrizes());
			//user.setParticipations(administrator.getParticipations());
			a = this.administratorRepository.save(a);
		} else {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			administrator.getUserAccount().setPassword(encoder.encodePassword(administrator.getUserAccount().getPassword(), null));
			a = this.administratorRepository.save(administrator);
		}
		return a;
	}

	public List<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final Integer arg0) {
		Assert.notNull(arg0);
		return this.administratorRepository.findOne(arg0);
	}

	public boolean exists(final Integer arg0) {
		Assert.notNull(arg0);
		return this.administratorRepository.exists(arg0);
	}

	/**
	 * Devuelve al administrador logueado
	 *
	 * @return manager
	 */
	public Administrator findPrincipal() {

		Assert.isTrue(LoginService.hasRole("ADMIN"));
		final Administrator admin = this.administratorRepository.findOneUserAccount(LoginService.getPrincipal().getId());
		return admin;
	}

	public Administrator findActorByUsername(final Integer id) {
		Assert.notNull(id);
		return this.administratorRepository.findOneUserAccount(id);
	}

	public User bannedUser(final User user) {
		Assert.notNull(user);
		Assert.isTrue(this.userService.exists(user.getId()));

		final UserAccount account = user.getUserAccount();
		account.setBanned(true);
		user.setUserAccount(account);

		return this.userService.save(user);
	}

	public User readmitUser(final User user) {
		Assert.notNull(user);
		Assert.isTrue(this.userService.exists(user.getId()));

		final UserAccount account = user.getUserAccount();
		account.setBanned(false);
		user.setUserAccount(account);

		return this.userService.save(user);
	}

	public List<Object> dashboard() {
		final List<Object> res = new LinkedList<Object>();

		res.add(this.userService.usersBanned().size() / this.userService.usersNotBanned().size());
		res.add(this.administratorRepository.prizesPerRaffle());
		res.add(this.administratorRepository.codesPerPrizes());
		res.add(this.administratorRepository.validCodesPerUser());
		res.add(this.administratorRepository.userWithMoreValidCodes());

		return res;
	}

}
