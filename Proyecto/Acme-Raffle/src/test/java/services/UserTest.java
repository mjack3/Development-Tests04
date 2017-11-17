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
import domain.User;
@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest extends AbstractTest {

	@Autowired
	private UserService	userService;
	
	// Tests
		// ====================================================

		/*
		 * En este caso de uso, el User inicia sesi�n en el sistema, a continuaci�n, intenta editar sus datos personales. 
		 * Para provocar el error, editaremos el perfil de una manera incorrecta (salt�ndonos alguna restricci�n), o intentaremos editar
		 * un perfil sin iniciar sesi�n.
		 * 
		*/
	
	protected void saveUserTest(String username,String phone, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			User User = userService.findPrincipal();
			User.setPhone(phone);
			userService.save(User);
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}
	
	@Test
	public void driverSaveUser() {

		Object testingData[][] = {
			// El User logueado todo correcto. -> true
			{
				"User1","+22(340)649311851", null
			},
			// Estamos autenticados pero el phone es incorrecto -> false
			{
				"User1", "(+34)649931185111",ConstraintViolationException.class
			}, {
				// Si no estamos autentificados como admin -> false
				null, "(+34)6499311851", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			saveUserTest((String) testingData[i][0], (String) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}

}
