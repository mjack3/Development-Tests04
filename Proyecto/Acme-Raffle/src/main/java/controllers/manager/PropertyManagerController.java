package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.PropertyService;
import domain.Property;
import forms.PropertyForm;

@RequestMapping("/property/manager")
@Controller
public class PropertyManagerController {

	@Autowired
	private PropertyService propertyService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int prizeId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {

			PropertyForm propertyForm = propertyService.createForm(prizeId);
			result = new ModelAndView("property/create");
			result.addObject("requestParam", "property/manager/create.do");
			result.addObject("propertyForm", propertyForm);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:raffle/manager/list.do");
			redirectAttrs.addFlashAttribute("message",
					"raffle.error.prize.exist");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final PropertyForm propertyForm,
			final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("property/create");
			result.addObject("requestParam", "property/manager/create.do");
			result.addObject("property", propertyForm);
		}

		else
			try {
				final Property propertyR = this.propertyService.reconstruct(
						propertyForm, binding);
				this.propertyService.save(propertyR);
				result = new ModelAndView(
						"redirect:/prize/manager/edit.do?prizeId="
								+ propertyForm.getPrizeId());
			} catch (final Throwable th) {
				result = new ModelAndView("property/create");
				result.addObject("requestParam", "property/manager/create.do");
				result.addObject("property", propertyForm);
				result.addObject("message", "property.commit.error");
			}
		return result;
	}

}
