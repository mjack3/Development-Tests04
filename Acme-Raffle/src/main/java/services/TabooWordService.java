package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.TabooWord;
import repositories.TabooWordRepository;

@Transactional
@Service
public class TabooWordService {

	@Autowired
	private TabooWordRepository	repository;
	
	public TabooWordService() {
		super();
	}

	public TabooWord create() {
		TabooWord word = new TabooWord();
		
		word.setName("");
		
		return word;
	}
	
	public TabooWord save(TabooWord entity) {
		Assert.notNull(entity);
		return repository.save(entity);
	}
	
	public TabooWord update(TabooWord entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		return repository.save(entity);
	}

	public List<TabooWord> findAll() {
		return repository.findAll();
	}

	public TabooWord findOne(Integer id) {
		Assert.notNull(id);
		Assert.isTrue(repository.exists(id));
		return repository.findOne(id);
	}

	public void delete(TabooWord entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		repository.delete(entity);
	}
	
	
	
}
