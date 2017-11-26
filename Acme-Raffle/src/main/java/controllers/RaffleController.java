
package controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Raffle;
import services.AuditReportService;
import services.RaffleService;

@RequestMapping("/raffle")
@Controller
public class RaffleController {

	@Autowired
	private RaffleService		raffleService;

	@Autowired
	private AuditReportService	auditReportService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Date today = new Date();
		result = new ModelAndView("raffle/list");
		result.addObject("requestURI", "raffle/list.do");
		final List<Raffle> raffle = this.raffleService.findAll();
		result.addObject("raffle", raffle);
		result.addObject("today", today);
		result.addObject("isManager", false);

		return result;
	}

	@RequestMapping(value = "/listAuditor", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam Integer q) {
		ModelAndView result;
		try {
			Date today = new Date();
			result = new ModelAndView("raffle/list");
			result.addObject("requestURI", "raffle/list.do");
			result.addObject("isManager", false);

			result.addObject("raffle", Arrays.asList(auditReportService.findOne(q).getRaffle()));
			result.addObject("today", today);
		} catch (Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

}
