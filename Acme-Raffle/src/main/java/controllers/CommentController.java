
package controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Comment;
import domain.Manager;
import domain.Prize;
import domain.Raffle;
import domain.TabooWord;
import domain.User;
import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.CommentService;
import services.ManagerService;
import services.PrizeService;
import services.RaffleService;
import services.TabooWordService;
import services.UserService;

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
	private TabooWordService		tabooWordService;

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
		List<Comment> commets = new LinkedList<Comment>();
		if (auditor != null)
			commets = auditor.getComments();
		if (manager != null)
			commets = manager.getComments();
		if (user != null)
			commets = user.getComments();
		if (admin != null)
			commets = admin.getComments();
		if (raffle != null)
			commets = raffle.getComments();
		if (prize != null)
			commets = prize.getComments();

		commets = hiddenTabooWords(commets);
		result.addObject("comments", commets);
		result.addObject("requestURI", "comment/list.do");
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

	@RequestMapping(value = "/createOnActor", method = RequestMethod.GET)
	public ModelAndView createOnActor(@RequestParam final int actorId) {

		ModelAndView resul;
		final Actor actor = this.actorService.findOne(actorId);
		final Comment comment = this.commentService.create();
		comment.setActor(actor);
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

	private List<Comment> hiddenTabooWords(List<Comment> comments) {
		List<Comment> res = new LinkedList<>();

		for (TabooWord t : tabooWordService.findAll()) {
			for (Comment c : comments) {
				if (c.getText().contains(t.getName())) {
					String text = "";
					String[] words = c.getText().split(" ");
					String chars = "";
					for (int i = 0; i < t.getName().length(); i++) {
						chars = chars + "*";
					}

					for (String w : words) {
						if (w.equalsIgnoreCase(t.getName())) {
							text += chars + " ";
						} else {
							text += w + " ";
						}
					}
					c.setText(text);
				}
				if (!res.contains(c))
					res.add(c);
			}
		}

		return res;
	}

	//AVGS
	@RequestMapping(value = "/avgactor/view", method = RequestMethod.GET)
	public ModelAndView avgsactor(@RequestParam Integer q) {
		ModelAndView result = new ModelAndView("actor/avgStar");

		result.addObject("avgStar", actorService.avgStarCommentsActor(q));

		return result;
	}

	@RequestMapping(value = "/avgraffle/view", method = RequestMethod.GET)
	public ModelAndView avgsraffle(@RequestParam Integer q) {
		ModelAndView result = new ModelAndView("raffle/avgStar");

		result.addObject("avgStar", raffleService.avgStarCommentsRaffle(q));

		return result;
	}

	@RequestMapping(value = "/avgprize/view", method = RequestMethod.GET)
	public ModelAndView avgsprize(@RequestParam Integer q) {
		ModelAndView result = new ModelAndView("prize/avgStar");

		result.addObject("avgStar", prizeService.avgStarCommentsPrize(q));

		return result;
	}

}
