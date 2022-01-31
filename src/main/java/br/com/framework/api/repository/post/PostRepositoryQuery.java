package br.com.framework.api.repository.post;

import java.util.List;

import br.com.framework.api.model.Post;
import br.com.framework.api.repository.filter.PostFilter;

public interface PostRepositoryQuery {

	public List<Post> filter(PostFilter postFilter);
}
