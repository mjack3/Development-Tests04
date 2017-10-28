
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PropertyService;

@RequestMapping("/property/administrator")
@Controller
public class PropertyAdministratorController {

	@Autowired
	private PropertyService	propertyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;

		res = new ModelAndView("property/list");

		res.addObject("properties", this.propertyService.findAll());

		return res;
	}

}
