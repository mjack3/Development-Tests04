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
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.AuditReport;
import domain.Raffle;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class AuditReportTest extends AbstractTest {

	@Autowired
	private AuditReportService	auditReportService;
	@Autowired
	private RaffleService		raffleService;


	// Tests
	// ====================================================

	protected void createAndSaveAuditReportTest(final String username, final int raffleId, final String text, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final Raffle raffle = this.raffleService.findOne(raffleId);
			final AuditReport auditReport = this.auditReportService.create();
			auditReport.setMoment(new Date(System.currentTimeMillis() - 1));
			auditReport.setRaffle(raffle);
			auditReport.setText(text);
			auditReport.setFinalMode(false);
			this.auditReportService.save(auditReport);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverCreateAndSaveAuditReport() {

		final Object testingData[][] = {

			{
				"auditor1", 129, "Texto para auditar", null
			},

			{
				"auditor1", 123333, "Texto para auditar", ConstraintViolationException.class
			}, {
				"auditor1", 129, "", ConstraintViolationException.class
			}, {
				"manager1", 129, "Texto11111", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.createAndSaveAuditReportTest((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void deleteAuditReportTest(final String username, final int auditReportId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final AuditReport auditReport = this.auditReportService.findOne(auditReportId);

			this.auditReportService.delete(auditReport);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverDeleteAuditReport() {

		final Object testingData[][] = {

			{
				"auditor1", 181, null
			},

			{
				"auditor1", 123333, IllegalArgumentException.class
			}, {
				"manager1", 181, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteAuditReportTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

}
