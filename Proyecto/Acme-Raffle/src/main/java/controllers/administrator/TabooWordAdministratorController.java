package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.TabooWord;
import services.TabooWordService;

@RequestMapping("/tabooword/administrator")
@Controller
public class TabooWordAdministratorController {

	@Autowired
	private TabooWordService tabooWordService;
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res;

		res = new ModelAndView("tabooword/list");
		
		res.addObject("taboowords", tabooWordService.findAll());

		return res;
	}
	
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView res;

		res = new ModelAndView("tabooword/create");
		
		res.addObject("tabooword", tabooWordService.create());

		return res;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Integer q) {
		ModelAndView res;

		res = new ModelAndView("tabooword/edit");
		
		res.addObject("tabooword", tabooWordService.findOne(q));

		return res;
	}
	
	@RequestMapping(value ="/save",method = RequestMethod.POST)
	public ModelAndView save(@Valid TabooWord q, BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("tabooword/create");
		
		if (binding.hasErrors()) {
			res = new ModelAndView("tabooword/create");
			res.addObject("tabooword", q);
			res.addObject("message", "commit.error");
		}else {
			try {
				tabooWordService.save(q);
				return list();
			}catch (Exception e) {
				res = new ModelAndView("tabooword/create");
				res.addObject("tabooword", q);
				res.addObject("message", "commit.error");
			}
		}


		return res;
	}
	
	@RequestMapping(value ="/saveEdit",method = RequestMethod.POST)
	public ModelAndView saveEdit(@Valid TabooWord q, BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("tabooword/edit");
		
		if (binding.hasErrors()) {
			res = new ModelAndView("tabooword/edit");
			res.addObject("tabooword", q);
			res.addObject("message", "commit.error");
		}else {
			try {
				tabooWordService.update(q);
				return list();
			}catch (Exception e) {
				res = new ModelAndView("tabooword/edit");
				res.addObject("tabooword", q);
				res.addObject("message", "commit.error");
			}
		}


		return res;
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Integer q) {
		try {
			TabooWord prop = tabooWordService.findOne(q);
			tabooWordService.delete(prop);
			return list();
		}catch (Exception e) {
			e.printStackTrace();
			return list();
		}
	}
}
