package br.com.framework.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.framework.api.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
 
}
