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
import services.PropertyService;

@RequestMapping("/property/administrator")
@Controller
public class PropertyAdministratorController {

	@Autowired
	private PropertyService propertyService;
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res;

		res = new ModelAndView("property/list");
		
		res.addObject("properties", propertyService.findAll());

		return res;
	}
	
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView res;

		res = new ModelAndView("property/create");
		
		res.addObject("property", propertyService.create());

		return res;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Integer q) {
		ModelAndView res;

		res = new ModelAndView("property/edit");
		
		res.addObject("property", propertyService.findOne(q));

		return res;
	}
	
	@RequestMapping(value ="/save",method = RequestMethod.POST)
	public ModelAndView save(@Valid Property q, BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("property/create");
		
		if (binding.hasErrors()) {
			res = new ModelAndView("property/create");
			res.addObject("property", q);
			res.addObject("message", "commit.error");
		}else {
			try {
				propertyService.save(q);
				return list();
			}catch (Exception e) {
				res = new ModelAndView("property/create");
				res.addObject("property", q);
				res.addObject("message", "commit.error");
			}
		}


		return res;
	}
	
	@RequestMapping(value ="/saveEdit",method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid Property q, BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("property/edit");
		
		if (binding.hasErrors()) {
			res = new ModelAndView("property/edit");
			res.addObject("property", q);
			res.addObject("message", "commit.error");
		}else {
			try {
				propertyService.update(q);
				return list();
			}catch (Exception e) {
				res = new ModelAndView("property/edit");
				res.addObject("property", q);
				res.addObject("message", "commit.error");
			}
		}


		return res;
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Integer q) {
		try {
			Property prop = propertyService.findOne(q);
			propertyService.delete(prop);
			return list();
		}catch (Exception e) {
			e.printStackTrace();
			return list();
		}
	}
}
