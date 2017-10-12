
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Prize;
import services.PrizeService;

@RequestMapping("/prize")
@Controller
public class PrizeController {

	@Autowired
	private PrizeService prizeService;


	@RequestMapping("/win")
	public ModelAndView view(@RequestParam Prize q) {
		ModelAndView res;

		res = new ModelAndView("prize/win");
		res.addObject("prize", q);

		return res;
	}

	@RequestMapping("/lose")
	public ModelAndView view() {
		ModelAndView res;

		res = new ModelAndView("prize/lose");

		return res;
	}

}
