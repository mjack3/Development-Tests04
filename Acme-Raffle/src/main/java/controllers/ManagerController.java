
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Manager;
import security.LoginService;
import services.ManagerService;
import utilities.Validator;

@Controller
@RequestMapping("/mana")
public class ManagerController extends AbstractController {

	@Autowired
	private ManagerService	managerService;
	@Autowired
	private LoginService	loginService;


	public ManagerController() {
		super();
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
		final ModelAndView result = new ModelAndView("mana/signup");
		Manager mana = null;
		String url = null;

		mana = this.managerService.create();
		url = "mana/save-mana-create.do";
		result.addObject("manager", mana);

		result.addObject("message", null);
		result.addObject("url", url);

		return result;
	}

	@RequestMapping(value = "/save-mana-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManagerCreate(@Valid final Manager mana, final BindingResult binding, final String message) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.signupModelAndView(mana, "mana.commit.error");
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
		} else
			try {
				if (Validator.isCorrectPhone(mana.getPhone())) {
					binding.rejectValue("phone", "error.phone", "error");
					throw new IllegalArgumentException();
				}
				this.managerService.save(mana);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.signupModelAndView(mana, "mana.commit.error");
			}
		return result;
	}

	protected ModelAndView signupModelAndView(final Manager mana) {

		ModelAndView result;
		result = this.signupModelAndView(mana, null);
		return result;
	}

	protected ModelAndView signupModelAndView(final Manager mana, final String message) {

		ModelAndView result;

		result = new ModelAndView("mana/signup");
		result.addObject("manager", mana);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int userAccountID) {
		ModelAndView result;
		Manager mana;
		try {
			Manager manager = (Manager) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

			mana = this.managerService.findManagerByUsername(userAccountID);
			Assert.isTrue(manager.getId() == mana.getId());

			result = this.createEditModelAndView(mana);
		} catch (Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/save-mana", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManager(@Valid final Manager mana, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
			result = this.createEditModelAndView(mana, "mana.commit.error");
		} else
			try {
				if (Validator.isCorrectPhone(mana.getPhone())) {
					binding.rejectValue("phone", "error.phone", "error");
					throw new IllegalArgumentException();
				}
				this.managerService.save(mana);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(mana, "mana.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager mana) {

		ModelAndView result;
		result = this.createEditModelAndView(mana, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager mana, final String message) {

		ModelAndView result;

		result = new ModelAndView("mana/edit");
		result.addObject("manager", mana);
		result.addObject("message", message);

		return result;
	}

}
