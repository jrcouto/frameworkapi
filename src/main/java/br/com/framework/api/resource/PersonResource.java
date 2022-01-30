package br.com.framework.api.resource;


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
import br.com.framework.api.model.Person;
import br.com.framework.api.repository.PersonRepository;
import br.com.framework.api.service.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PersonService personService;
	
	
	@GetMapping
	public Page<Person> search(Pageable pageable){ 
		return personRepository.findAll(pageable);
	} 
	  
	@PostMapping
	public ResponseEntity<Person> save(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person savedPerson = personRepository.save(person);

		//Lança o evento para retornar a URI no Header de retorno da requisição
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedPerson.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
	}  
	
	@GetMapping("/{id}")  
	public ResponseEntity<Person> getById(@PathVariable Integer id) {
		Person foundPerson = personRepository.findOne(id);
		return foundPerson ==null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(foundPerson);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		personRepository.delete(id); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Person> update(@PathVariable Integer id, @Valid @RequestBody Person person){
		Person savedPerson = personService.update(id, person);
		return ResponseEntity.ok(savedPerson); 
	}
}
