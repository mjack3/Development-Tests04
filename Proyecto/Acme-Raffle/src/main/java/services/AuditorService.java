package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Auditor;
import repositories.AuditorRepository;

@Service
@Transactional
public class AuditorService {

	@Autowired
	private AuditorRepository	repository;
	
	public AuditorService() {
		super();
	}

	public Auditor findOneUserAccount(int id) {
		Assert.notNull(id);
		return repository.findOneUserAccount(id);
	}

	public Auditor save(Auditor entity) {
		Assert.notNull(entity);
		return repository.save(entity);
	}
	
	public Auditor update(Auditor entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		return repository.save(entity);
	}

	public List<Auditor> findAll() {
		return repository.findAll();
	}

	public Auditor findOne(Integer arg0) {
		Assert.notNull(arg0);
		Assert.isTrue(repository.exists(arg0));
		return repository.findOne(arg0);
	}
	
	
	
	
}
