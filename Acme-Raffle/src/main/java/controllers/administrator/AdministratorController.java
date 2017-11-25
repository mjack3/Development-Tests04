
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import services.AdministratorService;
import utilities.Validator;

@RequestMapping("/administrator")
@Controller
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;


	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView res;

		res = new ModelAndView("administrator/dashboard");

		res.addObject("dashboard", this.administratorService.dashboard());

		res.addObject("avgStddevNumberCommentsPerActor", this.administratorService.avgStddevNumberStarPerActor());
		res.addObject("avgStddevNumberCommentsPerRaffle", this.administratorService.avgStddevNumberStarPerRaffle());
		res.addObject("avgStddevNumberCommentsPerPrize", this.administratorService.avgStddevNumberStarPerPrize());

		res.addObject("avgStddevNumberStarPerActor", this.administratorService.avgStddevNumberStarPerActor());
		res.addObject("avgStddevNumberStarPerRaffle", this.administratorService.avgStddevNumberStarPerRaffle());
		res.addObject("avgStddevNumberStarPerPrize", this.administratorService.avgStddevNumberStarPerPrize());

		res.addObject("avgNumberStarPerActorGroupByCountry", this.administratorService.avgNumberStarPerActorGroupByCountry());
		res.addObject("avgNumberStarPerActorGroupByCity", this.administratorService.avgNumberStarPerActorGroupByCity());

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int userAccountID) {
		ModelAndView result;
		Administrator admin;

		admin = this.administratorService.findActorByUsername(userAccountID);

		result = this.createEditModelAndView(admin);

		return result;
	}

	@RequestMapping(value = "/save-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
			result = this.createEditModelAndView(admin, "admin.commit.error");
		} else
			try {
				if (Validator.isCorrectPhone(admin.getPhone())) {
					binding.rejectValue("phone", "error.phone", "error");
					throw new IllegalArgumentException();
				}
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(admin, "admin.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin) {

		ModelAndView result;
		result = this.createEditModelAndView(admin, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin, final String message) {

		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", admin);
		result.addObject("message", message);

		System.out.println(message);

		return result;
	}

}
