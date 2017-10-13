
package controllers;

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
import domain.Prize;
import forms.PrizeForm;

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
	

	@RequestMapping(value = "/manager/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int raffleId) {
		ModelAndView result;

		result = createNewModelAndView(prizeService.createForm(raffleId), null);

		return result;
	}

	@RequestMapping(value = "/manager/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Prize prize, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(prize, null);
		} else {
			try {
				prizeService.save(prize);
				result = new ModelAndView("redirect:/prize/list.do");
			} catch (Throwable th) {
				result = createEditModelAndView(prize, "prize.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(Prize prize, String message) {
		ModelAndView result;
		result = new ModelAndView("prize/create");
		result.addObject("prize", prize);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int raffleId,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			result = new ModelAndView("prize/list");
			result.addObject("prize", prizeService.findAllByRaffleId(raffleId));
		} catch (Throwable oops) {
			result = new ModelAndView("raffle/manager/list");
			redirectAttrs.addFlashAttribute("message", "raffle.error.exist");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int prizeId,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);
			result = new ModelAndView("prize/edit");
			result.addObject("prize", prizeForm);
		} catch (Throwable oops) {
			result = new ModelAndView("raffle/manager/list");
			redirectAttrs.addFlashAttribute("message",
					"raffle.error.prize.exist");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid Prize prize) {
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

	protected ModelAndView createEditModelAndView(Prize prize, String message) {
		ModelAndView result = new ModelAndView("prize/edit");

		result.addObject("prize", prize);
		result.addObject("message", message);

		return result;
	}

}
