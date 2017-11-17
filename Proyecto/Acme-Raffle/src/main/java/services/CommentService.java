package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Comment;
import repositories.CommentRepository;

@Transactional
@Service
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;
	
	public CommentService() {
		super();
	}

	public void delete(Comment arg0) {
		Assert.notNull(arg0);
		Assert.isTrue(commentRepository.exists(arg0.getId()));
		commentRepository.delete(arg0);
	}

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	public Comment findOne(Integer arg0) {
		Assert.notNull(arg0);
		Assert.isTrue(commentRepository.exists(arg0));
		return commentRepository.findOne(arg0);
	}

	public Comment save(Comment arg0) {
		Assert.notNull(arg0);
		return commentRepository.save(arg0);
	}
	
	public Comment update(Comment arg0) {
		Assert.notNull(arg0);
		Assert.isTrue(commentRepository.exists(arg0.getId()));
		return commentRepository.save(arg0);
	}
	
	
}
