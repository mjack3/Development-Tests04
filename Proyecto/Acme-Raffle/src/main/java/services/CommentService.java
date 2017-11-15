
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import security.LoginService;
import domain.Administrator;
import domain.Auditor;
import domain.Comment;
import domain.Manager;
import domain.Prize;
import domain.Raffle;
import domain.TabooWord;
import domain.User;

@Transactional
@Service
public class CommentService {

	@Autowired
	private CommentRepository		commentRepository;

	@Autowired
	private TabooWordService		tabooWordService;

	@Autowired
	private LoginService			loginService;

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


	public CommentService() {
		super();
	}

	public void delete(final Comment arg0) {
		Assert.notNull(arg0);
		Assert.isTrue(this.commentRepository.exists(arg0.getId()));
		this.commentRepository.delete(arg0);
	}

	public List<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment findOne(final Integer arg0) {
		Assert.notNull(arg0);
		Assert.isTrue(this.commentRepository.exists(arg0));
		return this.commentRepository.findOne(arg0);
	}

	public Comment save(final Comment arg0) {
		Assert.notNull(arg0);
		return this.commentRepository.save(arg0);
	}

	public Comment update(final Comment arg0) {
		Assert.notNull(arg0);
		Assert.isTrue(this.commentRepository.exists(arg0.getId()));
		return this.commentRepository.save(arg0);
	}

	public Comment create() {
		// TODO Auto-generated method stub
		final Comment comment = new Comment();
		comment.setRating(0);
		return comment;
	}


	@Autowired
	private Validator	validator;


	public Comment reconstruct(final Comment comment, final BindingResult bindingResult) {
		// TODO Auto-generated method stub
		comment.setMomment(new Date(System.currentTimeMillis() - 1));

		this.validator.validate(comment, bindingResult);

		return comment;
	}

	public Collection<Comment> findAllInapropiate() {
		// TODO Auto-generated method stub
		final Collection<Comment> resul = new ArrayList<>();
		final Collection<Comment> comments = this.commentRepository.findAll();
		final Collection<TabooWord> tabooWords = this.tabooWordService.findAll();

		for (final TabooWord tabooWord : tabooWords)
			for (final Comment comment : comments)
				if (comment.getText().contentEquals(tabooWord.getName().toLowerCase()) || comment.getText().contentEquals(tabooWord.getName().toUpperCase()))
					resul.add(comment);

		return resul;
	}

	public void delete(final int commentId) {
		// TODO Auto-generated method stub
		Assert.isTrue(this.commentRepository.exists(commentId) && LoginService.hasRole("ADMIN"));

		final Comment comment = this.commentRepository.findOne(commentId);
		final Auditor auditor = this.auditorService.findOneByComment(commentId);
		final Manager manager = this.managerService.findOneByComment(commentId);
		final User user = this.userService.findOneByComment(commentId);
		final Administrator admin = this.administratorService.findOneByComment(commentId);
		final Raffle raffle = this.raffleService.findOneByComment(commentId);
		final Prize prize = this.prizeService.findOneByComment(commentId);

		if (auditor != null)
			auditor.getComments().remove(comment);
		if (manager != null)
			manager.getComments().remove(comment);
		if (user != null)
			user.getComments().remove(comment);
		if (admin != null)
			admin.getComments().remove(comment);
		if (raffle != null)
			raffle.getComments().remove(comment);
		if (prize != null)
			prize.getComments().remove(comment);

		this.commentRepository.delete(comment);

	}

}
