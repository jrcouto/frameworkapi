package br.com.framework.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.framework.api.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

	public List<Post> findByText(String text);
}
