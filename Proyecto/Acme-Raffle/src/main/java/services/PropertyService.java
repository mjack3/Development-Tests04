
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PropertyRepository;
import security.LoginService;
import domain.Prize;
import domain.Property;
import forms.PropertyForm;

@Transactional
@Service
public class PropertyService {

	@Autowired
	private PropertyRepository	repository;
	@Autowired
	private PrizeService		prizeService;


	public PropertyService() {
		super();
	}

	public Property create() {
		final Property res = new Property();

		res.setName("");

		return res;
	}

	public Property save(final Property entity) {
		Assert.isTrue(LoginService.hasRole("ADMINISTRATOR"));
		Assert.notNull(entity);
		return this.repository.save(entity);
	}

	public Property update(final Property entity) {
		Assert.notNull(entity);
		Assert.isTrue(this.repository.exists(entity.getId()));
		return this.repository.save(entity);
	}

	public List<Property> findAll() {
		return this.repository.findAll();
	}

	public Property findOne(final Integer id) {
		Assert.notNull(id);
		Assert.isTrue(this.repository.exists(id));
		return this.repository.findOne(id);
	}

	public void delete(final Property entity) {
		Assert.notNull(entity);
		Assert.isTrue(this.repository.exists(entity.getId()));

		for (final Prize p : this.prizeService.findAll())
			if (p.getProperties().contains(entity)) {
				final List<Property> props = (List<Property>) p.getProperties();
				props.remove(entity);
				p.setProperties(props);
				this.prizeService.save(p);
			}

		this.repository.delete(entity);
	}

	public PropertyForm createForm(final int prizeId) {
		Assert.isTrue(this.prizeService.exists(prizeId));
		final PropertyForm res = new PropertyForm();
		res.setPrizeId(prizeId);
		return res;
	}


	@Autowired
	private Validator	validator;


	public Property reconstruct(final PropertyForm propertyForm, final BindingResult binding) {
		final Property res = new Property();
		res.setName(propertyForm.getName());
		this.validator.validate(res, binding);
		return res;
	}

	public boolean exists(final int propertyId) {

		return this.repository.exists(propertyId);
	}

}
