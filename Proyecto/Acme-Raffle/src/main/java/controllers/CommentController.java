
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.CommentService;
import services.ManagerService;
import services.PrizeService;
import services.RaffleService;
import services.UserService;
import domain.Actor;
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
	@Autowired
	private ActorService			actorService;


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

	@RequestMapping(value = "/createOnRaffle", method = RequestMethod.GET)
	public ModelAndView createOnRaffle(@RequestParam final int raffleId) {

		ModelAndView resul;
		final Raffle raffle = this.raffleService.findOne(raffleId);
		final Comment comment = this.commentService.create();
		comment.setRaffle(raffle);
		resul = this.createEditModelAndView(comment, null);

		return resul;
	}

	@RequestMapping(value = "/createOnPrize", method = RequestMethod.GET)
	public ModelAndView createOnPrize(@RequestParam final int prizeId) {

		ModelAndView resul;
		final Prize prize = this.prizeService.findOne(prizeId);
		final Comment comment = this.commentService.create();
		comment.setPrize(prize);
		resul = this.createEditModelAndView(comment, null);

		return resul;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Comment comment, final BindingResult bindingResult) {
		ModelAndView resul;

		try {
			comment = this.commentService.reconstruct(comment, bindingResult);
			if (bindingResult.hasErrors())
				resul = this.createEditModelAndView(comment, null);
			else {

				if (comment.getActor() == null && comment.getRaffle() == null && comment.getPrize() == null)
					throw new IllegalArgumentException();

				final Comment saved = this.commentService.save(comment);

				if (comment.getRaffle() != null) {
					final Raffle raffle = comment.getRaffle();
					raffle.getComments().add(saved);
					this.raffleService.save(raffle);
				} else if (comment.getActor() != null) {
					final Actor actor = comment.getActor();
					actor.getComments().add(saved);
					this.actorService.save(actor);
				} else if (comment.getPrize() != null) {
					final Prize prize = comment.getPrize();
					prize.getComments().add(saved);
					this.prizeService.save(prize);
				}
				resul = new ModelAndView("redirect:/raffle/list.do");
			}

		} catch (final Throwable oops) {
			resul = this.createEditModelAndView(comment, "comment.commit.error");
		}

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
