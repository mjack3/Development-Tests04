/*
 * AbstractTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Property;

@Transactional
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PropertyTest extends AbstractTest {

	@Autowired
	private PropertyService propertyService;

	// Tests
	// ====================================================

	

	protected void createPropertyTest(String username, String name,
			Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			Property property = new Property();
			property.setName(name);
			propertyService.saveAF(property);
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}

	@Test
	public void drivercreateProperty() {

		Object testingData[][] = {
				//  OK
				{ "admin", "name", null },
				//
				{ "admin", null, ConstraintViolationException.class },
				
				{ null,"name", IllegalArgumentException.class }

		};

		for (int i = 0; i < testingData.length; i++) {
			createPropertyTest((String) testingData[i][0], (String) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}

}
