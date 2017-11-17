package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.User;
import services.AdministratorService;
import services.UserService;

@RequestMapping("/user/administrator")
@Controller
public class UserAdministratorController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdministratorService administratorService;

	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res;

		res = new ModelAndView("user/list");
		
		res.addObject("users", userService.findAll());
		res.addObject("bannedUsers", userService.usersBanned());

		return res;
	}
	
	@RequestMapping("/banned")
	public ModelAndView banned(@RequestParam User q) {
		
		try {
			administratorService.bannedUser(q);
			return list();
		}catch (Exception e) {
			return list();
		}

	}
	
	@RequestMapping("/readmit")
	public ModelAndView readmit(@RequestParam User q) {
		
		try {
			administratorService.readmitUser(q);
			return list();
		}catch (Exception e) {
			return list();
		}

	}
}
