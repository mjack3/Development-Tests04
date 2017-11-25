
package controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Raffle;
import domain.User;
import security.LoginService;
import services.RaffleService;

@Controller
@RequestMapping("/raffle/user")
public class RaffleUserController {

	@Autowired
	private RaffleService	raffleService;

	@Autowired
	private LoginService	loginService;


	@RequestMapping(value = "/listparticipation", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("raffle/listparticipation");
		result.addObject("requestURI2", "raffle/user/listparticipation.do");
		User user = (User) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

		List<Raffle> raffle = raffleService.raffleByParticpation(user.getId());
		result.addObject("isManager", false);

		result.addObject("raffle", raffle);

		return result;
	}

}
