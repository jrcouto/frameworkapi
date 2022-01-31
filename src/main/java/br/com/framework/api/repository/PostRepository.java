package br.com.framework.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.framework.api.model.Post;
import br.com.framework.api.repository.post.PostRepositoryQuery;

public interface PostRepository extends JpaRepository<Post, Integer>, PostRepositoryQuery{

	public List<Post> findByText(String text);
}
