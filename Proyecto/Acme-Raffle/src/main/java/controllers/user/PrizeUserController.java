
package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Raffle;
import services.PrizeService;
import services.RaffleService;

@Controller
@RequestMapping("/prize/user")
public class PrizeUserController {

	@Autowired
	private PrizeService	prizeService;
	@Autowired
	private RaffleService	raffleService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int q) {
		ModelAndView result;
		result = new ModelAndView("prize/list");
		result.addObject("requestURI", "prize/user/list.do");
		Raffle raffle = raffleService.findOne(q);

		result.addObject("prize", raffle.getPrizes());

		return result;
	}

}
