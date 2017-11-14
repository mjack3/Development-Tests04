
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Comment;

@Transactional
@Service
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;


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

}
