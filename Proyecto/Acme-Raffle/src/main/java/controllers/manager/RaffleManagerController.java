
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import services.RaffleService;
import controllers.AbstractController;
import domain.Prize;
import domain.Raffle;
import forms.RaffleForm;

@RequestMapping("/raffle/manager/")
@Controller
public class RaffleManagerController extends AbstractController {

	@Autowired
	RaffleService	raffleService;
	@Autowired
	ManagerService	managerService;


	public RaffleManagerController() {
		super();
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
				raffle.setPublicationTime(raffleForm.getPublicationTime());
				//genero premio

				final Prize prize = new Prize();
				prize.setName(raffleForm.getNamePrize());
				prize.setDescription(raffleForm.getDescriptionPrize());

				//genero código

				//Guardo rifa
				//Guardo Premios
				//Guardo los premios

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
		resul.addObject("requestURI", "raffle/manager/create.do");
		return resul;
	}
}
