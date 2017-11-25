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
public class AdministratorTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private UserService userService;
	
	// Tests
		// ====================================================

		/*
		 * En este caso de uso, el Administrator inicia sesión en el sistema, a continuación, intenta editar sus datos personales. 
		 * Para provocar el error, editaremos el perfil de una manera incorrecta (saltándonos alguna restricción), o intentaremos editar
		 * un perfil sin iniciar sesión.
		 * 
		*/
	
	protected void banUserTest(String username,int userId, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			authenticate(username);
			User user= userService.findOne(userId);
			
			administratorService.bannedUser(user);
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}
	
	@Test
	public void driverBanUser() {

		Object testingData[][] = {
			// El Administrator logueado todo correcto. -> true
			{
				"admin",123, null
			},
			// Estamos autenticados pero el user es incorrecto -> false
			{
				"admin", 1111,IllegalArgumentException.class
			}, {
				// Si no estamos autentificados como admin -> false
				null, 123, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			banUserTest((String) testingData[i][0], (int) testingData[i][1],(Class<?>) testingData[i][2]);
		}
	}

}
