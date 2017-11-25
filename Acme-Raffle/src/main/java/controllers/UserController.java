
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.User;
import services.UserService;
import utilities.Validator;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;


	public UserController() {
		super();
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
		final ModelAndView result = new ModelAndView("user/signup");
		User user = null;
		String url = null;

		user = this.userService.create();
		url = "user/save-user-create.do";
		result.addObject("user", user);

		result.addObject("message", null);
		result.addObject("url", url);

		return result;
	}

	@RequestMapping(value = "/save-user-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveUserCreate(@Valid final User user, final BindingResult binding, final String message) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.signupModelAndView(user, "user.commit.error");
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
		} else
			try {
				if (Validator.isCorrectPhone(user.getPhone())) {
					binding.rejectValue("phone", "error.phone", "error");
					throw new IllegalArgumentException();
				}
				this.userService.save(user);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.signupModelAndView(user, "user.commit.error");
			}
		return result;
	}

	protected ModelAndView signupModelAndView(final User user) {

		ModelAndView result;
		result = this.signupModelAndView(user, null);
		return result;
	}

	protected ModelAndView signupModelAndView(final User user, final String message) {

		ModelAndView result;

		result = new ModelAndView("user/signup");
		result.addObject("user", user);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int userAccountID) {
		ModelAndView result;
		User user;

		user = this.userService.findActorByUsername(userAccountID);

		result = this.createEditModelAndView(user);

		return result;
	}

	@RequestMapping(value = "/save-user", method = RequestMethod.POST, params = "save")
	public ModelAndView saveUser(@Valid final User user, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
			result = this.createEditModelAndView(user, "user.commit.error");
		} else
			try {

				if (Validator.isCorrectPhone(user.getPhone())) {
					binding.rejectValue("phone", "error.phone", "error");
					throw new IllegalArgumentException();
				}
				this.userService.save(user);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(user, "user.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final User user) {

		ModelAndView result;
		result = this.createEditModelAndView(user, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final User user, final String message) {

		ModelAndView result;

		result = new ModelAndView("user/edit");
		result.addObject("user", user);
		result.addObject("message", message);

		System.out.println(message);

		return result;
	}

}
