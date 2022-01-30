package br.com.framework.api.resource;


import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.framework.api.model.Comment;
import br.com.framework.api.repository.CommentRepository;
import br.com.framework.api.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentResource {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CommentService commentService;

	@GetMapping
	public Page<Comment> search(Pageable pageable){ 
		return commentRepository.findAll(pageable);
	} 
	  
	@PostMapping
	public ResponseEntity<Comment> save(@Valid @RequestBody Comment comment, HttpServletResponse response) {
		comment.setCommentDate(LocalDate.now());
		Comment savedComment = commentRepository.save(comment);

		//Lança o evento para retornar a URI no Header de retorno da requisição
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedComment.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
	}  
	
	@GetMapping("/{id}")  
	public ResponseEntity<Comment> getById(@PathVariable Integer id) {
		Comment foundComment = commentRepository.findOne(id);
		return foundComment ==null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(foundComment);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		commentRepository.delete(id); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Comment> update(@PathVariable Integer id, @Valid @RequestBody Comment comment){
		Comment savedComment = commentService.update(id, comment);
		return ResponseEntity.ok(savedComment); 
	}
}
