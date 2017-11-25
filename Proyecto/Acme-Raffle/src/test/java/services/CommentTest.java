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
import domain.Comment;
import domain.Raffle;
@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentTest extends AbstractTest {

	@Autowired
	private CommentService	commentService;
	@Autowired
	private RaffleService	raffleService;
	
	
	// Tests
		// ====================================================

	protected void createCommentTest(String text,Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			Raffle raffle = this.raffleService.findOne(123);
			Comment comment = this.commentService.create();
			comment.setRaffle(raffle);
			comment.setMoment(new Date());
			comment.setText(text);
			this.commentService.save(comment);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}
	
	@Test
	public void driverCreateComment() {

		Object testingData[][] = {
			
			{
				"Texto para comentar", null
			},
			
			{
				null,ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			createCommentTest((String) testingData[i][0],(Class<?>) testingData[i][1]);
		}
	}
	@Test
	public void listingInappropriateComment(){
		commentService.findAllInapropiate();
	}
	
	
	protected void deleteCommentTest(String username,int commentId,Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username);
			Comment comment = this.commentService.findOne(commentId);
			this.commentService.delete(comment);
			
			unauthenticate();
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}
	
	@Test
	public void driverDeleteComment() {

		Object testingData[][] = {
			
			{
				"admin",175, null
			},
			
			{
				"manager1",175,IllegalArgumentException.class
			},
			
			{
				"admin",1751111,IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			deleteCommentTest((String) testingData[i][0],(int) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}
	
}
