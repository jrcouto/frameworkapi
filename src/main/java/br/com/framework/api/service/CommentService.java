package br.com.framework.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.framework.api.model.Comment;
import br.com.framework.api.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;

	public Comment update(Integer id, Comment comment) {
		Comment savedComment = commentRepository.findOne(id);
		if(savedComment==null) {
			throw new EmptyResultDataAccessException(1);
		} 
		comment.setCommentDate(savedComment.getCommentDate()); //Garante que a data de inclusão do comentário será mantida
		BeanUtils.copyProperties(comment, savedComment, "id");
		return commentRepository.save(savedComment);
	}
}
