
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Participation;
import domain.User;
import repositories.UserRepository;
import security.Authority;
import security.UserAccount;

@Service
@Transactional
public class UserService {

	//User repositories

	@Autowired
	private UserRepository userRepository;


	//Constructor

	public UserService() {
		super();
	}

	//CRUD Methods

	public User create() {
		final User user = new User();

		user.setName(new String());
		user.setSurname(new String());
		user.setEmail(new String());
		user.setPhone(new String());
		user.setPostal(new String());
		user.setCity(new String());
		user.setCountry(new String());

		final Authority auth = new Authority();
		auth.setAuthority("USER");
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(auth));
		account.setUsername(new String());
		account.setPassword(new String());
		account.setBanned(false);

		user.setUserAccount(account);
		//user.setPrizes(new ArrayList<Prize>());
		user.setParticipations(new ArrayList<Participation>());

		return user;
	}

	public User save(final User user) {
		Assert.notNull(user);
		User u = null;

		if (this.exists(user.getId())) {
			u = this.findOne(user.getId());
			u.setName(user.getName());
			u.setCity(user.getCity());
			u.setCountry(user.getCountry());
			u.setEmail(user.getEmail());
			u.setPhone(user.getPhone());
			u.setPostal(user.getPostal());
			u.setSurname(user.getSurname());
			//user.setPrizes(user.getPrizes());
			user.setParticipations(user.getParticipations());
			u = this.userRepository.save(u);
		} else {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			user.getUserAccount().setPassword(encoder.encodePassword(user.getUserAccount().getPassword(), null));
			u = this.userRepository.save(user);
		}
		return u;
	}

	public User findActorByUsername(final Integer id) {
		Assert.notNull(id);
		return this.userRepository.findActorByUsernameId(id);
	}

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findOne(final Integer arg0) {
		Assert.notNull(arg0);
		return this.userRepository.findOne(arg0);
	}

	public boolean exists(final Integer arg0) {
		Assert.notNull(arg0);
		return this.userRepository.exists(arg0);
	}

	public List<User> usersBanned() {
		return this.userRepository.usersBanned();
	}

	public List<User> usersNotBanned() {
		return this.userRepository.usersNotBanned();
	}

}
