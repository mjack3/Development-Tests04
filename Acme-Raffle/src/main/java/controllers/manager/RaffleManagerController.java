
package controllers.manager;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Code;
import domain.Manager;
import domain.Prize;
import domain.Raffle;
import forms.RaffleForm;
import services.CodeService;
import services.ManagerService;
import services.PrizeService;
import services.PropertyService;
import services.RaffleService;

@RequestMapping("/raffle/managers/")
@Controller
public class RaffleManagerController extends AbstractController {

	@Autowired
	RaffleService			raffleService;
	@Autowired
	ManagerService			managerService;
	@Autowired
	PrizeService			prizeService;
	@Autowired
	CodeService				codeService;

	@Autowired
	private PropertyService	propertyService;


	public RaffleManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Date today = new Date();
		result = new ModelAndView("raffle/list");
		result.addObject("requestURI", "raffle/list.do");
		final Manager manag = this.managerService.findPrincipal();

		final Collection<Raffle> raffle = this.raffleService.findByManager(manag.getId());
		result.addObject("raffle", raffle);
		result.addObject("today", today);
		result.addObject("isManager", true);

		return result;
	}

	//	CREACION ==================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final RaffleForm raffleForm = new RaffleForm();
		final ModelAndView resul = this.createCreateModelAndView(raffleForm);

		return resul;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RaffleForm raffleForm, final BindingResult bindingResult) {
		ModelAndView resul;

		if (bindingResult.hasErrors())
			resul = this.createCreateModelAndView(raffleForm);
		else
			try {

				/*
				 * más comprobaciones
				 */

				if (raffleForm.getDeadline().before(raffleForm.getPublicationTime())) {
					bindingResult.rejectValue("deadline", "raffle.deadlineError", "error");
					throw new IllegalArgumentException();
				}
				if (raffleForm.getPublicationTime().before(Calendar.getInstance().getTime())) {
					bindingResult.rejectValue("publicationTime", "raffle.publicationTimeError", "error");
					throw new IllegalArgumentException();
				}

				if (raffleForm.getNum() < raffleForm.getNumWinner()) {
					bindingResult.rejectValue("num", "error.num", "error");
					throw new IllegalArgumentException();
				}

				final Raffle raffle = new Raffle();
				//	genero rifa
				raffle.setTitle(raffleForm.getTitle());
				raffle.setDescription(raffleForm.getDescription());
				raffle.setDeadline(raffleForm.getDeadline());
				raffle.setLogo(raffleForm.getLogo());
				raffle.setManager(this.managerService.findPrincipal());
				if (raffleForm.getPublicationTime() != null)
					raffle.setPublicationTime(raffleForm.getPublicationTime());
				//genero premio

				final Prize prize = new Prize();
				prize.setName(raffleForm.getNamePrize());
				prize.setDescription(raffleForm.getDescriptionPrize());

				//Guardo rifa

				final Raffle raffleSaved = this.raffleService.save(raffle);

				//Guardo Premios

				prize.setRaffle(raffleSaved);
				final Prize prizeSaved = this.prizeService.save(prize);

				//Genero y almaceno los códigos
				final Collection<Code> codeSaveds = this.codeService.getCodes(raffleForm.getNum(), raffleForm.getNumWinner(), raffleSaved, prizeSaved);

				resul = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				resul = this.createCreateModelAndView(raffleForm, "raffle.commit.error");
			}

		return resul;
	}
	private ModelAndView createCreateModelAndView(final RaffleForm raffleForm) {
		// TODO Auto-generated method stub
		return this.createCreateModelAndView(raffleForm, null);
	}

	private ModelAndView createCreateModelAndView(final RaffleForm raffleForm, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("raffle/create");

		resul.addObject("raffleForm", raffleForm);
		resul.addObject("message", message);
		resul.addObject("requestURI", "raffle/managers/create.do");
		return resul;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {
		ModelAndView result;
		try {
			Manager manag = this.managerService.findPrincipal();
			Assert.isTrue(manag.getRaffles().contains(this.raffleService.findOne(q)));
			result = this.createEditModelAndView(this.raffleService.findOne(q), null);
		} catch (Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid final Raffle raffle, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(raffle, null);
		else
			try {
				Manager manag = this.managerService.findPrincipal();
				Assert.isTrue(manag.getRaffles().contains(raffle));
				if (raffle.getDeadline().before(raffle.getPublicationTime())) {
					binding.rejectValue("deadline", "raffle.deadlineError", "error");
					throw new IllegalArgumentException();
				}

				if (raffle.getPublicationTime().before(Calendar.getInstance().getTime())) {
					binding.rejectValue("publicationTime", "raffle.publicationTimeError", "error");
					throw new IllegalArgumentException();
				}

				if (raffle.getPublicationTime() == null || !raffle.getDeadline().after(raffle.getPublicationTime())) {
					binding.rejectValue("publicationTime", "invalidDate", "invalidDate");
					throw new IllegalArgumentException();
				}

				this.raffleService.save(raffle);

				result = new ModelAndView("redirect:/raffle/managers/list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(raffle, "commit.error");
			}

		return result;
	}

	@Transactional
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int q) {
		ModelAndView result;
		final Date today = new Date();
		final Raffle raffle = this.raffleService.findOne(q);

		//Si aún no ha pasado la fecha de publicación, se puede borrar
		try {
			Manager manag = this.managerService.findPrincipal();
			Assert.isTrue(manag.getRaffles().contains(raffle));
			if (raffle.getPublicationTime().after(new Date()))
				this.raffleService.delete(raffle);
		} catch (final Throwable oops) {

		}

		result = new ModelAndView("raffle/list");
		result.addObject("requestURI", "raffle/list.do");
		final Manager manag = this.managerService.findPrincipal();
		final List<Raffle> raffles = this.raffleService.findByManager(manag.getId());
		result.addObject("raffle", raffles);
		result.addObject("today", today);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Raffle raffle, final String message) {
		final ModelAndView result = new ModelAndView("raffle/edit");

		result.addObject("raffle", raffle);
		result.addObject("message", message);

		return result;
	}
}
