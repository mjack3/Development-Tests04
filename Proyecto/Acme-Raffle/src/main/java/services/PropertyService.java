package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PropertyRepository;
import domain.Prize;
import domain.Property;
import forms.PropertyForm;

@Transactional
@Service
public class PropertyService {

	@Autowired
	private PropertyRepository repository;
	@Autowired
	private PrizeService prizeService;
	
	
	public PropertyService() {
		super();
	}
	
	public Property create() {
		Property res = new Property();
		
		res.setName("");
		
		return res;
	}

	public Property save(Property entity) {
		Assert.notNull(entity);
		return repository.save(entity);
	}
	
	public Property update(Property entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		return repository.save(entity);
	}

	public List<Property> findAll() {
		return repository.findAll();
	}

	public Property findOne(Integer id) {
		Assert.notNull(id);
		Assert.isTrue(repository.exists(id));
		return repository.findOne(id);
	}

	public void delete(Property entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		
		for(Prize p: prizeService.findAll()) {
			if(p.getProperties().contains(entity)) {
				List<Property> props = (List<Property>) p.getProperties();
				props.remove(entity);
				p.setProperties(props);
				prizeService.save(p);
			}
		}
		
		repository.delete(entity);
	}

	public PropertyForm createForm(int prizeId) {
		Assert.isTrue(prizeService.exists(prizeId));
		PropertyForm res = new PropertyForm();
		res.setPrizeId(prizeId);
		return res;
	}
	@Autowired
	private Validator validator;
	public Property reconstruct(PropertyForm propertyForm, BindingResult binding) {
		Property res = new Property();
		res.setName(propertyForm.getName());
		validator.validate(res, binding);
		return res;
	}

	public boolean exists(int propertyId) {
		
		return this.repository.exists(propertyId);
	}

	
	
}
