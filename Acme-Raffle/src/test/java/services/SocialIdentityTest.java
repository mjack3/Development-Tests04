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
import domain.SocialIdentity;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class SocialIdentityTest extends AbstractTest {

	@Autowired
	private SocialIdentityService	socialIdentityService;


	// Tests
	// ====================================================

	protected void createAndSaveSocialIdentityTest(final String username, final String url, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final SocialIdentity socialIdentity = this.socialIdentityService.create();
			socialIdentity.setNick("nick");
			socialIdentity.setUrl(url);
			this.socialIdentityService.save(socialIdentity);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverCreateAndSaveSocialIdentity() {

		final Object testingData[][] = {

			{
				"admin", "http://url.com", null
			},

			{
				"admin", "urlmala", null
			}, {
				"auditor1", "http://url.com", ConstraintViolationException.class
			}, {
				"", "http://url.com", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.createAndSaveSocialIdentityTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteSocialIdentityTest(final String username, final int socialIdentityId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final SocialIdentity socialIdentity = this.socialIdentityService.findOne(socialIdentityId);

			this.socialIdentityService.delete(socialIdentity);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverDeleteSocialIdentity() {

		final Object testingData[][] = {

			{
				"manager1", 151, null
			},

			{
				"auditor1", 151, IllegalArgumentException.class
			}, {
				"manager1", 15555, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteSocialIdentityTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

}
