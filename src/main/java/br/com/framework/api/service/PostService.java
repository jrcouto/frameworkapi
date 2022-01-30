package br.com.framework.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.framework.api.model.Post;
import br.com.framework.api.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;

	public Post update(Integer id, Post post) {
		Post savedPost = postRepository.findOne(id);
		if(savedPost==null) {
			throw new EmptyResultDataAccessException(1);
		} 
		post.setPublishDate(savedPost.getPublishDate()); //Garante que a data de publicação será mantida
		BeanUtils.copyProperties(post, savedPost, "id");
		return postRepository.save(savedPost);
	}
}
