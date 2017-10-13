package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Taxonomy;
import services.TaxonomyService;

@RequestMapping("/taxonomy/administrator")
@Controller
public class TaxonomyAdministratorController {

	@Autowired
	private TaxonomyService taxonomyService;
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res;

		res = new ModelAndView("taxonomy/list");
		
		res.addObject("taxonomies", taxonomyService.findAll());

		return res;
	}
	
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView res;

		res = new ModelAndView("taxonomy/create");
		
		res.addObject("taxonomy", taxonomyService.create());

		return res;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Integer q) {
		ModelAndView res;

		res = new ModelAndView("taxonomy/edit");
		
		res.addObject("taxonomy", taxonomyService.findOne(q));

		return res;
	}
	
	@RequestMapping(value ="/save",method = RequestMethod.POST)
	public ModelAndView save(@Valid Taxonomy q, BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("taxonomy/edit");
		
		if (binding.hasErrors()) {
			res = new ModelAndView("taxonomy/create");
			res.addObject("taxonomy", q);
			res.addObject("message", "commit.error");
		}else {
			try {
				taxonomyService.save(q);
				return list();
			}catch (Exception e) {
				res = new ModelAndView("taxonomy/create");
				res.addObject("taxonomy", q);
				res.addObject("message", "commit.error");
			}
		}


		return res;
	}
	
	@RequestMapping(value ="/saveEdit",method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid Taxonomy q, BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("taxonomy/edit");
		
		if (binding.hasErrors()) {
			res = new ModelAndView("taxonomy/create");
			res.addObject("taxonomy", q);
			res.addObject("message", "commit.error");
		}else {
			try {
				taxonomyService.update(q);
				return list();
			}catch (Exception e) {
				res = new ModelAndView("taxonomy/create");
				res.addObject("taxonomy", q);
				res.addObject("message", "commit.error");
			}
		}


		return res;
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Integer q) {
		try {
			taxonomyService.delete(taxonomyService.findOne(q));
			return list();
		}catch (Exception e) {
			return list();
		}
	}
}
