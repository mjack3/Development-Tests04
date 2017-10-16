
package controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RaffleService;
import domain.Raffle;

@RequestMapping("/raffle")
@Controller
public class RaffleController {

	@Autowired
	private RaffleService	raffleService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Date today = new Date();
		result = new ModelAndView("raffle/list");
		result.addObject("requestURI", "raffle/list.do");
		final List<Raffle> raffle = this.raffleService.findAll();
		result.addObject("raffle", raffle);
		result.addObject("today", today);

		return result;
	}

}
