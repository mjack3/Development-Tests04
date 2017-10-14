
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
import domain.Raffle;
import forms.PrizeForm;

@RequestMapping("/prize")
@Controller
public class PrizeController {

	@Autowired
	private PrizeService	prizeService;
	@Autowired
	private RaffleService	raffleService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int q) {
		ModelAndView result;
		result = new ModelAndView("prize/list");
		result.addObject("requestURI", "prize/list.do");
		final Raffle raffle = this.raffleService.findOne(q);
		result.addObject("prize", raffle.getPrizes());

		return result;
	}

	@RequestMapping("/win")
	public ModelAndView view(@RequestParam final Prize q) {
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
	public ModelAndView create(@RequestParam final int raffleId) {
		ModelAndView result;

		result = this.createEditModelAndView(this.prizeService.createForm(raffleId), null);

		return result;
	}

	protected ModelAndView createNewModelAndView(final Prize prize, final String message) {
		ModelAndView result;
		result = new ModelAndView("prize/create");
		result.addObject("prize", prize);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int raffleId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			result = new ModelAndView("prize/list");
			final List<Prize> prizes = this.prizeService.findAllByRaffleId(raffleId);
			result.addObject("prize", prizes);
			result.addObject("requestURI", "prize/manager/list.do");
			result.addObject("raffleId", raffleId);
			result.addObject("editable", this.raffleService.isEditable(raffleId));
		} catch (final Throwable oops) {
			result = new ModelAndView("raffle/list");
			redirectAttrs.addFlashAttribute("message", "raffle.error.exist");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int prizeId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			final PrizeForm prize = this.prizeService.reconstruct(prizeId);
			result = new ModelAndView("prize/edit");
			result.addObject("prize", prize);
		} catch (final Throwable oops) {
			result = new ModelAndView("raffle/manager/list");
			redirectAttrs.addFlashAttribute("message", "raffle.error.prize.exist");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid final PrizeForm prize) {
		ModelAndView result;

		try {
			this.prizeService.delete(prize);
			result = new ModelAndView("redirect:/prize/list.do");
		} catch (final Throwable th) {
			result = this.createEditModelAndView(prize, "prize.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final PrizeForm prize, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(prize, null);
		else
			try {
				final Prize prizeR = this.prizeService.reconstruct(prize, binding);
				final Prize prizeS = this.prizeService.save(prizeR);
				result = new ModelAndView("redirect:/prize/manager/list.do?raffleId=" + prizeS.getRaffle().getId());
			} catch (final Throwable th) {
				result = this.createEditModelAndView(prize, "prize.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final PrizeForm prize, final String message) {
		final ModelAndView result = new ModelAndView("prize/edit");
		result.addObject("requestParam", "prize/manager/edit.do");
		result.addObject("prize", prize);
		result.addObject("message", message);

		return result;
	}

}
