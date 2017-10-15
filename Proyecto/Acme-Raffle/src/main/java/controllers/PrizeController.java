package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CodeService;
import services.PrizeService;
import services.PropertyService;
import services.RaffleService;
import domain.Prize;
import domain.Raffle;
import forms.PrizeForm;

@RequestMapping("/prize")
@Controller
public class PrizeController {

	@Autowired
	private PrizeService prizeService;
	@Autowired
	private RaffleService raffleService;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private CodeService codeService;

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

	@RequestMapping(value = "/manager/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(final PrizeForm prizeForm,
			final BindingResult binding) {
		ModelAndView result;
		try {
			prizeService.checkNumberCodes(prizeForm, binding);

			final Prize prizeR = this.prizeService.reconstruct(prizeForm,
					binding);

			if (binding.hasErrors())
				result = this.createNewModelAndView(prizeForm, null);
			else {
				final Prize prizeS = this.prizeService.save(prizeR);

				this.codeService.getCodes(prizeForm.getTotal(),
						prizeForm.getWinners(),
						raffleService.findOne(prizeForm.getRaffleId()), prizeS);

				result = new ModelAndView(
						"redirect:/prize/manager/list.do?raffleId="
								+ prizeS.getRaffle().getId());
			}
		} catch (Throwable oops) {
			if (binding.hasErrors())
				result = this.createNewModelAndView(prizeForm, null);
			else
				result = this.createNewModelAndView(prizeForm,
						"prize.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/manager/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int raffleId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			result = this.createNewModelAndView(
					this.prizeService.createForm(raffleId), null);
		} catch (Throwable oops) {
			if (oops.getLocalizedMessage() == "raffle.error.exist") {

				result = new ModelAndView("redirect:/raffle/list.do");
				redirectAttrs
						.addFlashAttribute("message", "raffle.error.exist");
			} else if (oops.getLocalizedMessage() == "raffle.error.editable") {
				result = new ModelAndView("raffle/list");
				redirectAttrs.addFlashAttribute("message",
						"raffle.error.editable");
			} else {
				result = new ModelAndView("redirect:/raffle/list.do");
				redirectAttrs
						.addFlashAttribute("message", "prize.commit.error");
			}

		}

		return result;
	}

	@RequestMapping(value = "/manager/regCode", method = RequestMethod.GET)
	public ModelAndView regCode(@RequestParam final int prizeId,
			@RequestParam final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);
			result = new ModelAndView("prize/regCode");
			result.addObject("prizeForm", prizeForm);
			result.addObject("requestParam", "prize/manager/regCode.do");
		} catch (Throwable oops) {
			if (oops.getLocalizedMessage() == "raffle.error.prize.exist") {

				result = new ModelAndView("raffle/list");
				redirectAttrs.addFlashAttribute("message",
						"raffle.error.prize.exist");
			} else {
				result = new ModelAndView("raffle/list");
				redirectAttrs
						.addFlashAttribute("message", "prize.error.commit");
			}
		}

		return result;
	}

	@RequestMapping(value = "/manager/regCode", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegCode(final PrizeForm prizeForm,
			final BindingResult binding) {
		ModelAndView result;
		try {
			prizeService.checkNumberCodes(prizeForm, binding);

			if (binding.hasErrors())
				result = this.createNewModelAndView(prizeForm, null);
			else {
				this.prizeService.regCode(prizeForm);

				result = new ModelAndView(
						"redirect:/prize/manager/list.do?raffleId="
								+ prizeForm.getRaffleId());
			}
		} catch (Throwable oops) {
			if (binding.hasErrors()) {
				result = new ModelAndView("prize/regCode");
				result.addObject("prizeForm", prizeForm);
				result.addObject("requestParam", "prize/manager/regCode.do");
			} else {
				result = new ModelAndView("prize/regCode");
				result.addObject("prizeForm", prizeForm);
				result.addObject("requestParam", "prize/manager/regCode.do");
				result.addObject("message", "prize.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(final PrizeForm prizeForm,
			final String message) {
		ModelAndView result;
		result = new ModelAndView("prize/create");
		result.addObject("prizeForm", prizeForm);
		result.addObject("requestParam", "prize/manager/create.do");
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int raffleId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			result = new ModelAndView("prize/list");
			final List<Prize> prizes = this.prizeService
					.findAllByRaffleId(raffleId);
			result.addObject("prize", prizes);
			result.addObject("requestURI", "prize/manager/list.do");
			result.addObject("raffleId", raffleId);
			result.addObject("editable",
					this.raffleService.isEditable(raffleId));
		} catch (final Throwable oops) {
			result = new ModelAndView("raffle/list");
			redirectAttrs.addFlashAttribute("message", "raffle.error.exist");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int prizeId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {

			final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);
			result = new ModelAndView("prize/edit");
			result.addObject("properties", propertyService.findAll());
			result.addObject("requestParam", "prize/manager/edit.do");
			result.addObject("prizeForm", prizeForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/raffle/list.do");
			redirectAttrs.addFlashAttribute("message",
					"raffle.error.prize.exist");
		}

		return result;
	}

	@RequestMapping(value = "/manager/addProperty", method = RequestMethod.GET)
	public ModelAndView addProperty(@RequestParam final int prizeId,
			@RequestParam int propertyId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			Assert.isTrue(prizeService.exists(prizeId),
					"raffle.error.prize.exist");
			Assert.isTrue(propertyService.exists(propertyId),
					"prize.property.error");

			Prize prize = prizeService.findOne(prizeId);
			Assert.isTrue(raffleService.isEditable(prize.getRaffle().getId()),
					"raffle.error.editable");
			final domain.Property property = this.propertyService
					.findOne(propertyId);
			Collection<domain.Property> properties = prize.getProperties();
			properties.remove(property);
			properties.add(property);

			prize.setProperties(properties);
			prizeService.save(prize);

			final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);

			result = new ModelAndView("prize/edit");
			result.addObject("properties", propertyService.findAll());
			result.addObject("requestParam", "prize/manager/edit.do");
			result.addObject("prizeForm", prizeForm);
		} catch (final Throwable oops) {

			if (oops.getLocalizedMessage() == "raffle.error.prize.exist") {

				result = new ModelAndView("redirect:/raffle/list.do");
				redirectAttrs.addFlashAttribute("message",
						"raffle.error.prize.exist");
			} else if (oops.getLocalizedMessage() == "prize.property.error") {
				final PrizeForm prizeForm = this.prizeService
						.reconstruct(prizeId);
				result = new ModelAndView("prize/edit");
				result.addObject("properties", propertyService.findAll());
				result.addObject("requestParam", "prize/manager/edit.do");
				result.addObject("prizeForm", prizeForm);
				redirectAttrs.addFlashAttribute("message",
						"prize.property.error");
			} else if (oops.getLocalizedMessage() == "raffle.error.editable") {
				final PrizeForm prizeForm = this.prizeService
						.reconstruct(prizeId);
				result = new ModelAndView(
						"redirect:prize/manager/list.do?raffleId="
								+ prizeForm.getRaffleId());
				redirectAttrs.addFlashAttribute("message",
						"raffle.error.prize.exist");
			} else {
				final PrizeForm prizeForm = this.prizeService
						.reconstruct(prizeId);

				result = new ModelAndView("prize/edit");
				result.addObject("properties", propertyService.findAll());
				result.addObject("requestParam", "prize/manager/edit.do");
				result.addObject("prizeForm", prizeForm);
				redirectAttrs
						.addFlashAttribute("message", "prize.commit.error");
			}

		}

		return result;
	}

	@RequestMapping(value = "/manager/removeProperty", method = RequestMethod.GET)
	public ModelAndView removeProperty(@RequestParam final int prizeId,
			@RequestParam int propertyId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			Assert.isTrue(prizeService.exists(prizeId),
					"raffle.error.prize.exist");
			Assert.isTrue(propertyService.exists(propertyId),
					"prize.property.error");

			Prize prize = prizeService.findOne(prizeId);
			Assert.isTrue(raffleService.isEditable(prize.getRaffle().getId()),
					"raffle.error.editable");
			final domain.Property property = this.propertyService
					.findOne(propertyId);
			Collection<domain.Property> properties = prize.getProperties();
			properties.remove(property);
			prize.setProperties(properties);
			prizeService.save(prize);

			final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);

			result = new ModelAndView("prize/edit");
			result.addObject("properties", propertyService.findAll());
			result.addObject("requestParam", "prize/manager/edit.do");
			result.addObject("prizeForm", prizeForm);
		} catch (final Throwable oops) {

			final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);

			if (oops.getLocalizedMessage() == "raffle.error.prize.exist") {

				result = new ModelAndView("redirect:/raffle/list.do");
				redirectAttrs.addFlashAttribute("message",
						"raffle.error.prize.exist");
			} else if (oops.getLocalizedMessage() == "prize.property.error") {

				result = new ModelAndView("prize/edit");
				result.addObject("properties", propertyService.findAll());
				result.addObject("requestParam", "prize/manager/edit.do");
				result.addObject("prizeForm", prizeForm);
				redirectAttrs.addFlashAttribute("message",
						"prize.property.error");
			} else if (oops.getLocalizedMessage() == "raffle.error.editable") {
				result = new ModelAndView(
						"redirect:prize/manager/list.do?raffleId="
								+ prizeForm.getRaffleId());
				redirectAttrs.addFlashAttribute("message",
						"raffle.error.prize.exist");
			} else {
				result = new ModelAndView("prize/edit");
				result.addObject("properties", propertyService.findAll());
				result.addObject("requestParam", "prize/manager/edit.do");
				result.addObject("prizeForm", prizeForm);
				redirectAttrs
						.addFlashAttribute("message", "prize.commit.error");
			}

		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid final PrizeForm prizeForm) {
		ModelAndView result;

		try {
			this.prizeService.delete(prizeForm);
			result = new ModelAndView(
					"redirect:/prize/manager/list.do?raffleId="
							+ prizeForm.getRaffleId());
		} catch (final Throwable th) {
			result = this.createEditModelAndView(prizeForm,
					"prize.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final PrizeForm prizeForm,
			final BindingResult binding) {
		ModelAndView result;
		try {
			final Prize prizeR = this.prizeService.reconstruct(prizeForm,
					binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(prizeForm, null);
			else {
				final Prize prizeS = this.prizeService.save(prizeR);
				result = new ModelAndView(
						"redirect:/prize/manager/list.do?raffleId="
								+ prizeS.getRaffle().getId());
			}
		} catch (Throwable oops) {
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(prizeForm, null);
			} else {
				result = this.createEditModelAndView(prizeForm,
						"prize.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final PrizeForm prizeForm,
			final String message) {
		final ModelAndView result = new ModelAndView("prize/edit");
		result.addObject("properties", propertyService.findAll());
		result.addObject("requestParam", "prize/manager/edit.do");
		result.addObject("prizeForm", prizeForm);
		result.addObject("message", message);

		return result;
	}

}
