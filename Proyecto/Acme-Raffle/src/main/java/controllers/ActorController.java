
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.User;
import security.LoginService;
import services.UserService;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private LoginService	loginService;
	@Autowired
	private UserService		userService;

	/*
	 * @Autowired
	 * private AdministratorService administratorService;
	 *
	 * @Autowired
	 * private ManagerService managerService;
	 */


	public ActorController() {
		super();
	}

	/*
	 * @RequestMapping(value = "/edit", method = RequestMethod.GET)
	 * public ModelAndView edit(final int userAccountID) {
	 * ModelAndView result;
	 * Actor actor;
	 *
	 * actor = this.loginService.findActorByUserName(userAccountID);
	 *
	 * result = this.createEditModelAndView(actor);
	 *
	 * return result;
	 * }
	 */

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup(@RequestParam final int q) {
		final ModelAndView result = new ModelAndView("actor/signup");
		Actor actor = null;
		String url = null;

		if (q == 1) {
			actor = this.userService.create();
			url = "actor/save-user-create.do";
		} else if (q == 2) {
			//actor= managerService.create();
			//url="actor/save-manager-create.do";
		}

		result.addObject("actor", actor);
		result.addObject("user", actor);
		//result.addObject("manager", actor);
		result.addObject("message", null);
		result.addObject("url", url);
		result.addObject("type", q);

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
		//result.addObject("administrator", actor);
		result.addObject("user", actor);
		//result.addObject("manager", actor);
		result.addObject("message", message);

		return result;
	}

	/*
	 * @RequestMapping(value = "/save-administrator", method = RequestMethod.POST, params = "save")
	 * public ModelAndView saveAdministrator(@Valid Administrator actor, BindingResult binding) {
	 * ModelAndView result;
	 *
	 * if (binding.hasErrors()) {
	 * result = createEditModelAndView(actor);
	 * } else {
	 * try {
	 * administratorService.save(actor);
	 * result = new ModelAndView("redirect:/welcome/index.do");
	 * } catch (Throwable oops) {
	 * result = createEditModelAndView(actor, "actor.commit.error"); } }
	 *
	 * return result; }
	 */
	@RequestMapping(value = "/save-user", method = RequestMethod.POST, params = "save")
	public ModelAndView saveUser(@Valid final User actor, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				this.userService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable e) {
				result = this.createEditModelAndView(actor, "actor.commit.error");
			}
		return result;
	}

	/*
	 * @RequestMapping(value="/save-manager", method = RequestMethod.POST, params = "save")
	 * public ModelAndView saveManager(@Valid Manager actor, BindingResult binding){
	 * ModelAndView result;
	 *
	 * if(binding.hasErrors()){
	 * result = createEditModelAndView(actor);
	 * }else{
	 * try{
	 * managerService.save(actor);
	 * result = new ModelAndView("redirect:/welcome/index.do");
	 * }catch(Throwable e){
	 * result = createEditModelAndView(actor, "actor.commit.error");
	 * }
	 * }
	 * return result;
	 * }
	 **/

	@RequestMapping(value = "/save-user-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveUserCreate(@Valid final User actor, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {

			result = new ModelAndView("actor/signup");
			result.addObject("actor", actor);
			result.addObject("user", actor);
			//result.addObject("manager", actor);
			result.addObject("message", null);

			result.addObject("url", "actor/save-user-create.do");
		} else
			try {
				this.userService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable e) {
				result = new ModelAndView("actor/signup");
				result.addObject("actor", actor);
				result.addObject("user", actor);
				//result.addObject("manager", actor);
				result.addObject("message", "actor.commit.error");
				//result.addObject("url", "actor/save-manager-create.do");
			}
		return result;
	}

	/*
	 * @RequestMapping(value="/save-manager-create", method = RequestMethod.POST, params = "save")
	 * public ModelAndView saveManagerCreate(@Valid Manager actor, BindingResult binding){
	 * ModelAndView result;
	 * if(binding.hasErrors()){
	 * result= new ModelAndView("actor/signup");
	 * result.addObject("actor", actor);
	 * result.addObject("user", actor);
	 * result.addObject("trainer", actor);
	 * result.addObject("manager", actor);
	 * result.addObject("message", null);
	 * result.addObject("url", "actor/save-manager-create.do");
	 * }else{
	 * try{
	 * managerService.save(actor);
	 * result = new ModelAndView("redirect:/welcome/index.do");
	 * }catch(Throwable e){
	 * result= new ModelAndView("actor/signup");
	 * result.addObject("actor", actor);
	 * result.addObject("user", actor);
	 * result.addObject("trainer", actor);
	 * result.addObject("manager", actor);
	 * result.addObject("message", "actor.commit.error");
	 * result.addObject("url", "actor/save-manager-create.do");
	 * }
	 * }
	 * return result;
	 * }
	 */

}