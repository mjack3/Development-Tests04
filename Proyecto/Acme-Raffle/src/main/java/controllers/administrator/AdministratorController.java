package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;

@RequestMapping("/administrator")
@Controller
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView res;

		res = new ModelAndView("administrator/dashboard");
		
		res.addObject("dashboard", administratorService.dashboard());

		return res;
	}
}
