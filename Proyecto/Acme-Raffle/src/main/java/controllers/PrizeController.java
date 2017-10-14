
package controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.PrizeService;
import services.RaffleService;
import domain.Prize;
import forms.PrizeForm;

@RequestMapping("/prize")
@Controller
public class PrizeController {

	@Autowired
	private PrizeService prizeService;
	@Autowired 
	private RaffleService raffleService;


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
	

	@RequestMapping(value = "/manager/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int raffleId) {
		ModelAndView result;

		result = createEditModelAndView(prizeService.createForm(raffleId), null);

		return result;
	}


	protected ModelAndView createNewModelAndView(Prize prize, String message) {
		ModelAndView result;
		result = new ModelAndView("prize/create");
		result.addObject("prize", prize);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int raffleId,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			result = new ModelAndView("prize/list");
			List <Prize>prizes= prizeService.findAllByRaffleId(raffleId);
			result.addObject("prize",prizes);
			result.addObject("requestURI", "prize/manager/list.do");
			result.addObject("raffleId", raffleId);
			result.addObject("editable",raffleService.isEditable(raffleId));
		} catch (Throwable oops) {
			result = new ModelAndView("raffle/list");
			redirectAttrs.addFlashAttribute("message", "raffle.error.exist");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int prizeId,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			PrizeForm prize = this.prizeService.reconstruct(prizeId);
			result = new ModelAndView("prize/edit");
			result.addObject("prize", prize);
		} catch (Throwable oops) {
			result = new ModelAndView("raffle/manager/list");
			redirectAttrs.addFlashAttribute("message",
					"raffle.error.prize.exist");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid PrizeForm prize) {
		ModelAndView result;

		try {
			prizeService.delete(prize);
			result = new ModelAndView("redirect:/prize/list.do");
		} catch (Throwable th) {
			result = createEditModelAndView(prize, "prize.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(PrizeForm prize, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(prize, null);
		} else {
			try {
				Prize prizeR = prizeService.reconstruct(prize, binding);
				Prize prizeS = prizeService.save(prizeR);
				result = new ModelAndView("redirect:/prize/manager/list.do?raffleId="
						+ prizeS.getRaffle().getId());
			} catch (Throwable th) {
				result = createEditModelAndView(prize, "prize.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(PrizeForm prize, String message) {
		ModelAndView result = new ModelAndView("prize/edit");
		result.addObject("requestParam", "prize/manager/edit.do");
		result.addObject("prize", prize);
		result.addObject("message", message);

		return result;
	}

}
