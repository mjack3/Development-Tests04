
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.SocialIdentity;
import security.LoginService;
import services.SocialIdentityService;

@Controller
@RequestMapping("/socialidentity/actor")
public class ActorSocialIdentityController {

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private LoginService			loginService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("socialIdentity/list");
		Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Collection<SocialIdentity> socialIdentity = actor.getSocialIdentities();
		result.addObject("socialIdentity", socialIdentity);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(socialIdentityService.create(), null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int q) {
		ModelAndView result;
		Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (actor.getSocialIdentities().contains(socialIdentityService.findOne(q))) {
			result = createEditModelAndView(socialIdentityService.findOne(q), null);
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int q) {
		ModelAndView result;
		SocialIdentity socialIdentity = socialIdentityService.findOne(q);
		Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (actor.getSocialIdentities().contains(socialIdentity)) {
			socialIdentityService.delete(socialIdentity);
			result = new ModelAndView("redirect:/socialidentity/actor/list.do");

		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveCreate(@Valid SocialIdentity socialIdentity, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createNewModelAndView(socialIdentity, null);
		} else {
			try {
				socialIdentityService.save(socialIdentity);

				result = new ModelAndView("redirect:/socialidentity/actor/list.do");
			} catch (Exception e) {
				result = createNewModelAndView(socialIdentity, "commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid SocialIdentity socialIdentity, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(socialIdentity, null);
		} else {
			try {
				Actor actor = this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
				Assert.isTrue(actor.getSocialIdentities().contains(socialIdentity));
				socialIdentityService.save(socialIdentity);

				result = new ModelAndView("redirect:/socialidentity/actor/list.do");
			} catch (Exception e) {
				result = createEditModelAndView(socialIdentity, "commit.error");
			}
		}

		return result;
	}

	protected ModelAndView createNewModelAndView(SocialIdentity socialIdentity, String message) {
		ModelAndView result;
		result = new ModelAndView("socialIdentity/create");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity, String message) {
		ModelAndView result = new ModelAndView("socialIdentity/edit");

		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", message);

		return result;
	}

}
