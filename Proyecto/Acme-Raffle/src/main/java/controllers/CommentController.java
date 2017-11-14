
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.AuditorService;
import services.CommentService;
import services.ManagerService;
import services.PrizeService;
import services.RaffleService;
import services.UserService;
import domain.Administrator;
import domain.Auditor;
import domain.Comment;
import domain.Manager;
import domain.Prize;
import domain.Raffle;
import domain.User;

@RequestMapping("/comment")
@Controller
public class CommentController {

	@Autowired
	private CommentService			commentService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private RaffleService			raffleService;

	@Autowired
	private PrizeService			prizeService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer q) {
		ModelAndView result;

		final Auditor auditor = this.auditorService.findOne(q);
		final Manager manager = this.managerService.findOne(q);
		final User user = this.userService.findOne(q);
		final Administrator admin = this.administratorService.findOne(q);
		final Raffle raffle = this.raffleService.findOne(q);
		final Prize prize = this.prizeService.findOne(q);
		result = new ModelAndView("comment/list");

		if (auditor != null)
			result.addObject("comments", auditor.getComments());
		if (manager != null)
			result.addObject("comments", manager.getComments());
		if (user != null)
			result.addObject("comments", user.getComments());
		if (admin != null)
			result.addObject("comments", admin.getComments());
		if (raffle != null)
			result.addObject("comments", raffle.getComments());
		if (prize != null)
			result.addObject("comments", prize.getComments());

		return result;
	}
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int raffleId) {

		ModelAndView resul;
		final Raffle raffle = this.raffleService.findOne(raffleId);
		final Comment comment = this.commentService.create();
		comment.setRaffle(raffle);
		resul = this.createEditModelAndView(comment, null);

		return resul;
	}

	private ModelAndView createEditModelAndView(final Comment comment, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("comment/edit");
		resul.addObject("comment", comment);
		resul.addObject("message", message);

		resul.addObject("actionParam", "comment/edit.do");

		return resul;
	}

}
