
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Comment;
import domain.TabooWord;
import services.CommentService;
import services.TabooWordService;

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

		List<Comment> comments = this.commentService.findAll();
		comments = hiddenTabooWords(comments);
		resul.addObject("comments", comments);
		resul.addObject("requestURI", "comment/administrator/list.do");
		return resul;
	}

	@RequestMapping(value = "/listbad", method = RequestMethod.GET)
	public ModelAndView listBad() {
		final ModelAndView resul = new ModelAndView("comment/list");

		List<Comment> comments = (List<Comment>) this.commentService.findAllInapropiate();
		comments = hiddenTabooWords(comments);
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
	public Collection<Comment> findAllInapropiate() {
		// TODO Auto-generated method stub
		final Collection<Comment> resul = new ArrayList<>();
		final Collection<Comment> comments = this.commentService.findAll();
		final Collection<TabooWord> tabooWords = this.tabooWordService.findAll();

		for (final TabooWord tabooWord : tabooWords) {
			for (final Comment comment : comments) {
				String[] words = comment.getText().split(" ");

				for (String s : words) {
					if (s.equalsIgnoreCase(tabooWord.getName())) {
						resul.add(comment);
					}
				}
			}

		}

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

}
