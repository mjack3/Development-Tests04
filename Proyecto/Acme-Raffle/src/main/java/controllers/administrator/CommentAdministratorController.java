
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.TabooWordService;
import controllers.AbstractController;
import domain.Comment;

@RequestMapping("/comment/administrator")
@Controller
public class CommentAdministratorController extends AbstractController {

	public CommentAdministratorController() {
		super();
	}


	@Autowired
	private CommentService		commentService;
	@Autowired
	private TabooWordService	tabooWordService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView resul = new ModelAndView("comment/list");

		final Collection<Comment> comments = this.commentService.findAll();
		resul.addObject("comments", comments);
		resul.addObject("requestURI", "comment/administrator/list.do");
		return resul;
	}

	@RequestMapping(value = "/listbad", method = RequestMethod.GET)
	public ModelAndView listBad() {
		final ModelAndView resul = new ModelAndView("comment/list");

		final Collection<Comment> comments = this.commentService.findAllInapropiate();

		resul.addObject("comments", comments);
		return resul;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int commentId) {

		ModelAndView resul;

		try {
			this.commentService.delete(commentId);
			resul = this.list();

		} catch (final Throwable oops) {

			resul = this.list();
			resul.addObject("message", "comment.commit.error");
		}

		return resul;
	}

}
