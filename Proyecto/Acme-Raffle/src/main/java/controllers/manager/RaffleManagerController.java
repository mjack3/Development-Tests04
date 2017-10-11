
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import services.RaffleService;
import controllers.AbstractController;
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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final RaffleForm raffleForm = new RaffleForm();
		final ModelAndView resul = this.createEditModelAndView(raffleForm);

		return resul;
	}

	private ModelAndView createEditModelAndView(final RaffleForm raffleForm) {
		// TODO Auto-generated method stub
		return this.createEditModelAndView(raffleForm, null);
	}

	private ModelAndView createEditModelAndView(final RaffleForm raffleForm, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("raffle/create");
		resul.addObject("raffleForm", raffleForm);
		resul.addObject("message", message);
		resul.addObject("requestURI", "raffle/manager/edit.do");
		return null;
	}
}
