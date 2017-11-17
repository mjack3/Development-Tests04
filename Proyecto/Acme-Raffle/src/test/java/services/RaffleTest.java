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

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Prize;
import domain.Raffle;

@Transactional
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RaffleTest extends AbstractTest {

	@Autowired
	private RaffleService raffleService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private PrizeService prizeService;
	@Autowired
	private CodeService codeService;

	// Tests
	// ====================================================

	

	protected void createRaffleTest(String username, 
			Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			final Raffle raffle = new Raffle();
			// genero rifa
			raffle.setTitle("title");
			raffle.setDescription("description");
			raffle.setDeadline(new Date(System.currentTimeMillis() + 86400000));
			raffle.setLogo("http://logo.com");
			raffle.setManager(this.managerService.findPrincipal());

			raffle.setPublicationTime(new Date());
			// genero premio

			final Prize prize = new Prize();
			prize.setName("name");
			prize.setDescription("descriptionPrize");

			// Guardo rifa

			final Raffle raffleSaved = this.raffleService.save(raffle);

			// Guardo Premios

			prize.setRaffle(raffleSaved);
			final Prize prizeSaved = this.prizeService.save(prize);

			// Genero y almaceno los códigos
			this.codeService.getCodes(20, 3, raffleSaved, prizeSaved);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}

	@Test
	public void drivercreateRaffle() {

		Object testingData[][] = {
				//  OK
				{ "manager1",  null },
				//
				{ "user1",  IllegalArgumentException.class },
				{ null, IllegalArgumentException.class }

		};

		for (int i = 0; i < testingData.length; i++) {
			createRaffleTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

}
