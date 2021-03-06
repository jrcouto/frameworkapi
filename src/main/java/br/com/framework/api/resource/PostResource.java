package br.com.framework.api.resource;


import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.framework.api.event.ResourceCreatedEvent;
import br.com.framework.api.model.Post;
import br.com.framework.api.repository.PostRepository;
import br.com.framework.api.repository.filter.PostFilter;
import br.com.framework.api.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostResource {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PostService postService;
	
	
	@GetMapping("/listAll") 
	@PreAuthorize("hasAuthority('ROLE_SEARCH_POST') and #oauth2.hasScope('read')")
	public Page<Post> list(Pageable pageable){ 
		return postRepository.findAll(pageable); 
	}
	
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_SEARCH_POST') and #oauth2.hasScope('read')")
	public List<Post> search(PostFilter postFilter, Pageable pageable){ 
		return postRepository.filter(postFilter);
	} 
	  
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_INSERT_POST') and #oauth2.hasScope('write')")
	public ResponseEntity<Post> save(@Valid @RequestBody Post post, HttpServletResponse response) {
		post.setPublishDate(LocalDate.now());
		Post savedPost = postRepository.save(post);
		
		//Lan??a o evento para retornar a URI no Header de retorno da requisi????o
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedPost.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPost); 
	}  
	
	@GetMapping("/{id}")  
	@PreAuthorize("hasAuthority('ROLE_SEARCH_POST') and #oauth2.hasScope('read')")
	public ResponseEntity<Post> getById(@PathVariable Integer id) {
		Post foundPost = postRepository.findOne(id);
		return foundPost ==null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(foundPost);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_DELETE_POST') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		postRepository.delete(id);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_UPDATE_POST') and #oauth2.hasScope('write')")
	public ResponseEntity<Post> update(@PathVariable Integer id, @Valid @RequestBody Post post){
		post.setLastUpdateDate(LocalDate.now());
		Post savedPost = postService.update(id, post);
		return ResponseEntity.ok(savedPost); 
	}
}
