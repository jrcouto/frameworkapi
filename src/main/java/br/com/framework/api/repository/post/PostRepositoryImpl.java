package br.com.framework.api.repository.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.framework.api.model.Post;
import br.com.framework.api.repository.filter.PostFilter;

public class PostRepositoryImpl implements PostRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Post> filter(PostFilter postFilter) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Post> criteria = builder.createQuery(Post.class);
		
		Root<Post> root = criteria.from(Post.class);
		
		Predicate[] predicates = createRestrictions(postFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Post> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] createRestrictions(PostFilter postFilter, CriteriaBuilder builder, Root<Post> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(postFilter.getText())) {
			predicates.add(builder.like(builder.lower(root.get("text")),"%"+postFilter.getText().toLowerCase()+"%"));
		}
		
		if(!StringUtils.isEmpty(postFilter.getPublishDateFrom())) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("publishDate"), postFilter.getPublishDateFrom()));
		}
		
		if(!StringUtils.isEmpty(postFilter.getPublishDateTo())) {
			predicates.add(builder.lessThanOrEqualTo(root.get("publishDate"), postFilter.getPublishDateTo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	
	
}
