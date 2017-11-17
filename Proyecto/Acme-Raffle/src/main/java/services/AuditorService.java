
package services;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.UserAccount;
import domain.Auditor;
import forms.AuditorForm;

@Service
@Transactional
public class AuditorService {

	@Autowired
	private AuditorRepository	repository;


	public AuditorService() {
		super();
	}

	public Auditor findOneUserAccount(final int id) {
		Assert.notNull(id);
		return this.repository.findOneUserAccount(id);
	}

	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);
		return this.repository.save(auditor);
	}

	public Auditor update(final Auditor entity) {
		Assert.notNull(entity);
		Assert.isTrue(this.repository.exists(entity.getId()));
		return this.repository.save(entity);
	}

	public List<Auditor> findAll() {
		return this.repository.findAll();
	}

	public Auditor findOne(final Integer arg0) {
		Assert.notNull(arg0);
		Assert.isTrue(this.repository.exists(arg0));
		return this.repository.findOne(arg0);
	}

	public Auditor reconstruct(final AuditorForm auditorForm) {
		// TODO Auto-generated method stub
		final Auditor auditor = this.create();

		auditor.setName(auditorForm.getName());
		auditor.setSurname(auditorForm.getSurname());
		auditor.setCity(auditorForm.getCity());
		auditor.setCountry(auditorForm.getCountry());
		auditor.setEmail(auditorForm.getEmail());
		auditor.setPhone(auditorForm.getPhone());
		auditor.setPostal(auditorForm.getPostal());
		auditor.getUserAccount().setUsername(auditorForm.getUsername());
		auditor.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(auditorForm.getPassword(), null));
		return auditor;
	}

	private Auditor create() {
		// TODO Auto-generated method stub
		final Auditor auditor = new Auditor();
		final UserAccount userAccount = new UserAccount();
		auditor.setUserAccount(userAccount);

		final Authority authority = new Authority();
		authority.setAuthority("AUDITOR");
		userAccount.setAuthorities(Arrays.asList(authority));
		userAccount.setBanned(false);
		return auditor;
	}

}
