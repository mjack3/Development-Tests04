
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Property;
import security.LoginService;
import services.PropertyService;

@RequestMapping("/property/administrator")
@Controller
public class PropertyAdministratorController {

	@Autowired
	private PropertyService	propertyService;
	@Autowired
	private LoginService	loginService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;

		res = new ModelAndView("property/list");

		res.addObject("properties", this.propertyService.findAll());

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView resul;

		final Property property = new Property();
		resul = this.createEditModelAndView(property);

		return resul;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {

		ModelAndView resul;
		try {
			final Property property = this.propertyService.findOne(q);

			resul = this.createEditModelAndView(property);
		} catch (Throwable e) {
			resul = new ModelAndView("redirect:/welcome/index.do");

		}

		return resul;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Property property, final BindingResult bindingResult) {

		ModelAndView resul;

		if (bindingResult.hasErrors())
			resul = this.createEditModelAndView(property);
		else
			try {

				this.propertyService.save(property);
				resul = this.list();

			} catch (final Throwable oops) {
				resul = this.createEditModelAndView(property, "property.commit.error");
			}

		return resul;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int q) {
		ModelAndView resul;
		try {

			final Property property = this.propertyService.findOne(q);
			this.propertyService.delete(property);
			resul = this.list();

		} catch (final Throwable oops) {
			resul = this.list();
			resul.addObject("message", "property.commit.error");
		}

		return resul;
	}

	private ModelAndView createEditModelAndView(final Property property) {
		// TODO Auto-generated method stub
		return this.createEditModelAndView(property, null);
	}

	private ModelAndView createEditModelAndView(final Property property, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("property/administrator/edit");

		resul.addObject("property", property);
		resul.addObject("requestParam", "property/administrator/edit.do");
		resul.addObject("message", message);
		return resul;
	}

}
