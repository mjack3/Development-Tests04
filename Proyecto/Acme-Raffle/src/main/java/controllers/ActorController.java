
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.Manager;
import domain.User;
import security.LoginService;
import services.AdministratorService;
import services.ManagerService;
import services.UserService;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private LoginService			loginService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ManagerService			managerService;


	public ActorController() {
		super();
	}

	//----------------------------------------------------
	//						REGISTRAR
	//----------------------------------------------------

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup(@RequestParam final int q) {
		final ModelAndView result = new ModelAndView("actor/signup");
		User user = null;
		Manager manager = null;
		String url = null;

		if (q == 1) {
			user = this.userService.create();
			url = "actor/save-user-create.do";
			result.addObject("user", user);
		} else if (q == 2) {
			manager = this.managerService.create();
			url = "actor/save-mana-create.do";
			result.addObject("actor", manager);
		}

		result.addObject("message", null);
		result.addObject("url", url);
		result.addObject("type", q);

		return result;
	}

	@RequestMapping(value = "/save-mana-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManagerCreate(@Valid final Manager actor, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.signupModelAndView(actor, "actor.commit.error");
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
		} else
			try {
				this.managerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.signupModelAndView(actor, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView signupModelAndView(final Actor actor) {

		ModelAndView result;
		result = this.signupModelAndView(actor, null);
		return result;
	}

	protected ModelAndView signupModelAndView(final Actor actor, final String message) {

		ModelAndView result;

		result = new ModelAndView("actor/signup");
		result.addObject("user", actor);
		result.addObject("message", message);

		return result;
	}

	//----------------------------------------------------
	//						EDITAR
	//----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int userAccountID) {
		ModelAndView result;
		Actor actor;

		actor = this.loginService.findActorByUsername(userAccountID);

		result = this.createEditModelAndView(actor);

		return result;
	}

	@RequestMapping(value = "/save-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid final Administrator actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor, "actor.commit.error");
		//for (final ObjectError e : binding.getAllErrors())
		//System.out.println(e.toString());
		else
			try {
				this.administratorService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/save-mana", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManager(@Valid final Manager actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(actor, "actor.commit.error");
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
		} else
			try {
				this.managerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable e) {

				result = this.createEditModelAndView(actor, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor) {

		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {

		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("administrator", actor);
		result.addObject("user", actor);
		result.addObject("manager", actor);
		result.addObject("message", message);

		System.out.println(message);

		return result;
	}

}
