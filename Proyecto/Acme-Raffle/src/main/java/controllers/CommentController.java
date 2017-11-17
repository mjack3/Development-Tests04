package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.Auditor;
import domain.Manager;
import domain.Prize;
import domain.Raffle;
import domain.User;
import services.AdministratorService;
import services.AuditorService;
import services.CommentService;
import services.ManagerService;
import services.PrizeService;
import services.RaffleService;
import services.UserService;

@RequestMapping("/comment")
@Controller
public class CommentController {
	
	@Autowired
	private CommentService	commentService;
	
	@Autowired
	private AuditorService	auditorService;
	
	@Autowired
	private RaffleService	raffleService;
	
	@Autowired
	private PrizeService	prizeService;
	
	@Autowired
	private ManagerService	managerService;
	
	@Autowired
	private UserService		userService;
	
	@Autowired
	private AdministratorService		administratorService;

	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam Integer q) {
		ModelAndView result;

		Auditor auditor = auditorService.findOne(q);
		Manager manager = managerService.findOne(q);
		User user = userService.findOne(q);
		Administrator admin = administratorService.findOne(q);
		Raffle raffle = raffleService.findOne(q);
		Prize prize= prizeService.findOne(q);
		result = new ModelAndView("comment/list");
		
		if(auditor!=null)
			result.addObject("comments", auditor.getComments());
		if(manager!=null)
			result.addObject("comments", manager.getComments());
		if(user!=null)
			result.addObject("comments", user.getComments());
		if(admin!=null)
			result.addObject("comments", admin.getComments());
		if(raffle!=null)
			result.addObject("comments", raffle.getComments());
		if(prize!=null)
			result.addObject("comments", prize.getComments());

		return result;
	}
}
