package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Taxonomy;
import repositories.TaxonomyRepository;

@Transactional
@Service
public class TaxonomyService {

	@Autowired
	TaxonomyRepository repository;
	
	public TaxonomyService() {
		super();
	}
	
	public Taxonomy create() {
		Taxonomy res = new Taxonomy();
		
		res.setProperty("");
		
		return res;
	}

	public Taxonomy save(Taxonomy entity) {
		Assert.notNull(entity);
		return repository.save(entity);
	}
	
	public Taxonomy update(Taxonomy entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		return repository.save(entity);
	}

	public List<Taxonomy> findAll() {
		return repository.findAll();
	}

	public Taxonomy findOne(Integer id) {
		Assert.notNull(id);
		Assert.isTrue(repository.exists(id));
		return repository.findOne(id);
	}

	public void delete(Taxonomy entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		repository.delete(entity);
	}

	
	
}
