
package controllers;

import java.util.ArrayList;
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

import security.LoginService;
import services.CodeService;
import services.PrizeService;
import services.PropertyService;
import services.RaffleService;
import domain.Code;
import domain.Participation;
import domain.Prize;
import domain.User;
import forms.PrizeForm;

@RequestMapping("/prize")
@Controller
public class PrizeController extends AbstractController {

	@Autowired
	private PrizeService	prizeService;
	@Autowired
	private RaffleService	raffleService;
	@Autowired
	private PropertyService	propertyService;
	@Autowired
	private CodeService		codeService;
	@Autowired
	private LoginService	loginService;


	public PrizeController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int q) {
		ModelAndView result;
		result = new ModelAndView("prize/list");
		result.addObject("requestURI", "prize/list.do");
		final List<Prize> prizes = this.prizeService.findAllByRaffleId(q);
		result.addObject("prize", prizes);
		result.addObject("raffleId", q);
		result.addObject("editable", this.raffleService.isEditable(q));
		return result;
	}

	@RequestMapping("/win")
	public ModelAndView view(@RequestParam final Prize q) {
		ModelAndView res;

		res = new ModelAndView("prize/win");
		final User user = (User) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		final List<String> userCode = new ArrayList<String>();
		final List<String> prizeCode = new ArrayList<String>();
		String codewiner = "";

		for (final Participation p : user.getParticipations())
			userCode.add(p.getUsedCode());

		for (final Code c : q.getCodes()) {
			prizeCode.add(c.getCode());
			if (c.getIsWinner() == true)
				codewiner = c.getCode();
		}

		userCode.retainAll(prizeCode);

		if (userCode.contains(codewiner))
			res.addObject("prize", q);
		else
			res = new ModelAndView("redirect:/raffle/list.do");

		return res;
	}

	@RequestMapping("/lose")
	public ModelAndView view() {
		ModelAndView res;

		res = new ModelAndView("prize/lose");

		return res;
	}

	@RequestMapping(value = "/manager/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(final PrizeForm prizeForm, final BindingResult binding) {
		ModelAndView result;
		try {
			this.prizeService.checkNumberCodes(prizeForm, binding);

			final Prize prizeR = this.prizeService.reconstruct(prizeForm, binding);

			if (binding.hasErrors())
				result = this.createNewModelAndView(prizeForm, null);
			else {
				final Prize prizeS = this.prizeService.save(prizeR);

				this.codeService.getCodes(prizeForm.getTotal(), prizeForm.getWinners(), this.raffleService.findOne(prizeForm.getRaffleId()), prizeS);

				result = new ModelAndView("redirect:/prize/manager/list.do?q=" + prizeS.getRaffle().getId());
			}
		} catch (final Throwable oops) {
			if (binding.hasErrors())
				result = this.createNewModelAndView(prizeForm, null);
			else
				result = this.createNewModelAndView(prizeForm, "prize.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/manager/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int raffleId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			result = this.createNewModelAndView(this.prizeService.createForm(raffleId), null);
		} catch (final Throwable oops) {
			if (oops.getLocalizedMessage() == "raffle.error.exist") {

				result = new ModelAndView("redirect:/raffle/list.do");
				redirectAttrs.addFlashAttribute("message", "raffle.error.exist");
			} else if (oops.getLocalizedMessage() == "raffle.error.editable") {
				result = new ModelAndView("redirect:/raffle/list.do");
				redirectAttrs.addFlashAttribute("message", "raffle.error.editable");
			} else {
				result = new ModelAndView("redirect:/raffle/list.do");
				redirectAttrs.addFlashAttribute("message", "prize.commit.error");
			}

		}

		return result;
	}

	@RequestMapping(value = "/manager/regCode", method = RequestMethod.GET)
	public ModelAndView regCode(@RequestParam final int prizeId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);
			result = new ModelAndView("prize/regCode");
			result.addObject("prizeForm", prizeForm);
			result.addObject("requestParam", "prize/manager/regCode.do");
		} catch (final Throwable oops) {
			if (oops.getLocalizedMessage() == "raffle.error.prize.exist") {

				result = new ModelAndView("redirect:raffle/list.do");
				redirectAttrs.addFlashAttribute("message", "raffle.error.prize.exist");
			} else {
				result = new ModelAndView("redirect:raffle/list.do");
				redirectAttrs.addFlashAttribute("message", "prize.error.commit");
			}
		}

		return result;
	}

	@RequestMapping(value = "/manager/regCode", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegCode(final PrizeForm prizeForm, final BindingResult binding) {
		ModelAndView result;

		try {
			this.prizeService.checkNumberCodes(prizeForm, binding);

			if (binding.hasErrors())
				result = this.createNewModelAndView(prizeForm, null);
			else {
				this.prizeService.regCode(prizeForm);

				result = new ModelAndView("redirect:/prize/manager/list.do?q=" + prizeForm.getRaffleId());
			}
		} catch (final Throwable oops) {
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

	protected ModelAndView createNewModelAndView(final PrizeForm prizeForm, final String message) {
		ModelAndView result;
		result = new ModelAndView("prize/create");
		result.addObject("prizeForm", prizeForm);
		result.addObject("requestParam", "prize/manager/create.do");
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int q, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			result = new ModelAndView("prize/list");
			final List<Prize> prizes = this.prizeService.findAllByRaffleId(q);
			result.addObject("prize", prizes);
			result.addObject("requestURI", "prize/manager/list.do");
			result.addObject("raffleId", q);
			result.addObject("editable", this.raffleService.isEditable(q));
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

			final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);
			result = this.createEditModelAndView(prizeForm, null);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/raffle/list.do");
			if (oops.getLocalizedMessage() == "raffle.error.editable")
				redirectAttrs.addFlashAttribute("message", "raffle.error.editable");
			else if (oops.getLocalizedMessage() == "raffle.error.prize.exist")
				redirectAttrs.addFlashAttribute("message", "raffle.error.prize.exist");
			else
				redirectAttrs.addFlashAttribute("message", "prize.error.commit");
		}

		return result;
	}

	@RequestMapping(value = "/manager/addProperty", method = RequestMethod.GET)
	public ModelAndView addProperty(@RequestParam final int prizeId, @RequestParam final int propertyId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			Assert.isTrue(this.prizeService.exists(prizeId), "raffle.error.prize.exist");
			Assert.isTrue(this.propertyService.exists(propertyId), "prize.property.error");

			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.isTrue(this.raffleService.isEditable(prize.getRaffle().getId()), "raffle.error.editable");
			final domain.Property property = this.propertyService.findOne(propertyId);
			final Collection<domain.Property> properties = prize.getProperties();
			properties.remove(property);
			properties.add(property);

			prize.setProperties(properties);
			this.prizeService.save(prize);

			final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);

			result = new ModelAndView("prize/edit");
			result.addObject("properties", this.propertyService.findAll());
			result.addObject("requestParam", "prize/manager/edit.do");
			result.addObject("prizeForm", prizeForm);
		} catch (final Throwable oops) {

			if (oops.getLocalizedMessage() == "raffle.error.prize.exist") {

				result = new ModelAndView("redirect:/raffle/list.do");
				redirectAttrs.addFlashAttribute("message", "raffle.error.prize.exist");
			} else if (oops.getLocalizedMessage() == "prize.property.error") {
				final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);
				result = new ModelAndView("prize/edit");
				result.addObject("properties", this.propertyService.findAll());
				result.addObject("requestParam", "prize/manager/edit.do");
				result.addObject("prizeForm", prizeForm);
				redirectAttrs.addFlashAttribute("message", "prize.property.error");
			} else if (oops.getLocalizedMessage() == "raffle.error.editable") {
				final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);
				result = new ModelAndView("redirect:prize/manager/list.do?q=" + prizeForm.getRaffleId());
				redirectAttrs.addFlashAttribute("message", "raffle.error.prize.exist");
			} else {
				final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);

				result = new ModelAndView("prize/edit");
				result.addObject("properties", this.propertyService.findAll());
				result.addObject("requestParam", "prize/manager/edit.do");
				result.addObject("prizeForm", prizeForm);
				redirectAttrs.addFlashAttribute("message", "prize.commit.error");
			}

		}

		return result;
	}

	@RequestMapping(value = "/manager/removeProperty", method = RequestMethod.GET)
	public ModelAndView removeProperty(@RequestParam final int prizeId, @RequestParam final int propertyId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			Assert.isTrue(this.prizeService.exists(prizeId), "raffle.error.prize.exist");
			Assert.isTrue(this.propertyService.exists(propertyId), "prize.property.error");

			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.isTrue(this.raffleService.isEditable(prize.getRaffle().getId()), "raffle.error.editable");
			final domain.Property property = this.propertyService.findOne(propertyId);
			final Collection<domain.Property> properties = prize.getProperties();
			properties.remove(property);
			prize.setProperties(properties);
			this.prizeService.save(prize);

			final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);

			result = new ModelAndView("prize/edit");
			result.addObject("properties", this.propertyService.findAll());
			result.addObject("requestParam", "prize/manager/edit.do");
			result.addObject("prizeForm", prizeForm);
		} catch (final Throwable oops) {

			final PrizeForm prizeForm = this.prizeService.reconstruct(prizeId);

			if (oops.getLocalizedMessage() == "raffle.error.prize.exist") {

				result = new ModelAndView("redirect:/raffle/list.do");
				redirectAttrs.addFlashAttribute("message", "raffle.error.prize.exist");
			} else if (oops.getLocalizedMessage() == "prize.property.error") {

				result = new ModelAndView("prize/edit");
				result.addObject("properties", this.propertyService.findAll());
				result.addObject("requestParam", "prize/manager/edit.do");
				result.addObject("prizeForm", prizeForm);
				redirectAttrs.addFlashAttribute("message", "prize.property.error");
			} else if (oops.getLocalizedMessage() == "raffle.error.editable") {
				result = new ModelAndView("redirect:prize/manager/list.do?q=" + prizeForm.getRaffleId());
				redirectAttrs.addFlashAttribute("message", "raffle.error.prize.exist");
			} else {
				result = new ModelAndView("prize/edit");
				result.addObject("properties", this.propertyService.findAll());
				result.addObject("requestParam", "prize/manager/edit.do");
				result.addObject("prizeForm", prizeForm);
				redirectAttrs.addFlashAttribute("message", "prize.commit.error");
			}

		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid final PrizeForm prizeForm) {
		ModelAndView result;

		try {
			this.prizeService.delete(prizeForm);
			result = new ModelAndView("redirect:/prize/manager/list.do?q=" + prizeForm.getRaffleId());
		} catch (final Throwable th) {
			result = this.createEditModelAndView(prizeForm, "prize.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final PrizeForm prizeForm, final BindingResult binding) {
		ModelAndView result;
		try {
			final Prize prizeR = this.prizeService.reconstruct(prizeForm, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(prizeForm, null);
			else {
				final Prize prizeS = this.prizeService.save(prizeR);
				result = new ModelAndView("redirect:/prize/manager/list.do?q=" + prizeS.getRaffle().getId());
			}
		} catch (final Throwable oops) {
			if (binding.hasErrors())
				result = this.createEditModelAndView(prizeForm, null);
			else
				result = this.createEditModelAndView(prizeForm, "prize.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final PrizeForm prizeForm, final String message) {
		final ModelAndView result = new ModelAndView("prize/edit");
		result.addObject("properties", this.propertyService.findAll());
		result.addObject("requestParam", "prize/manager/edit.do");
		result.addObject("prizeForm", prizeForm);
		result.addObject("message", message);

		return result;
	}

}
