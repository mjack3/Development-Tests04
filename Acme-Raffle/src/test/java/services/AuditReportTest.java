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
	private RaffleService	raffleService;
	
	
	// Tests
		// ====================================================

	protected void createAndSaveAuditReportTest(String username, int raffleId,String text,Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			Raffle raffle = this.raffleService.findOne(raffleId);
			AuditReport auditReport = this.auditReportService.create();
			auditReport.setMoment(new Date(System.currentTimeMillis() - 1));
			auditReport.setRaffle(raffle);
			auditReport.setText(text);
			auditReport.setFinalMode(false);
			this.auditReportService.save(auditReport);
			
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}
	
	@Test
	public void driverCreateAndSaveAuditReport() {

		Object testingData[][] = {
			
			{
				"auditor1",129,"Texto para auditar", null
			},
			
			{
				"auditor1",123333,"Texto para auditar", ConstraintViolationException.class
			},
			{
				"auditor1",129,"", ConstraintViolationException.class
			},
			{
				"manager1",129,"Texto11111", ConstraintViolationException.class
			}
			
			
		};

		for (int i = 0; i < testingData.length; i++) {
			createAndSaveAuditReportTest((String) testingData[i][0],(int) testingData[i][1],(String) testingData[i][2],(Class<?>) testingData[i][3]);
		}
	}
	
	protected void deleteAuditReportTest(String username,int auditReportId,Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			
			AuditReport auditReport = this.auditReportService.findOne(auditReportId);
			
			this.auditReportService.delete(auditReport);
			
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}
	
	@Test
	public void driverDeleteAuditReport() {

		Object testingData[][] = {
			
			{
				"auditor1",181, null
			},
			
			{
				"auditor1",123333, IllegalArgumentException.class
			},
			{
				"manager1",181, IllegalArgumentException.class
			}
			
			
		};

		for (int i = 0; i < testingData.length; i++) {
			deleteAuditReportTest((String) testingData[i][0],(int) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}
	

}
