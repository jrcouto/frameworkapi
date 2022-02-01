package br.com.framework.api.resource;


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
import br.com.framework.api.model.PhotoGalery;
import br.com.framework.api.repository.PhotoGaleryRepository;
import br.com.framework.api.service.PhotoGaleryService;

@RestController
@RequestMapping("/photo_galeries")
public class PhotoGaleryResource {

	@Autowired
	private PhotoGaleryRepository photoGaleryRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PhotoGaleryService photoGaleryService;
	
	
	@GetMapping("/person/{personId}") 
	@PreAuthorize("hasAuthority('ROLE_SEARCH_PHOTO_GALERY') and #oauth2.hasScope('read')")
	public List<PhotoGalery> getByPersonId(@PathVariable Integer personId){ 
		return photoGaleryRepository.findByPersonId(personId);
	}
	
	@GetMapping("/listAll") 
	@PreAuthorize("hasAuthority('ROLE_SEARCH_PHOTO_GALERY') and #oauth2.hasScope('read')")
	public Page<PhotoGalery> list(Pageable pageable){ 
		return photoGaleryRepository.findAll(pageable); 
	}
	
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_INSERT_PHOTO_GALERY') and #oauth2.hasScope('write')")
	public ResponseEntity<PhotoGalery> save(@Valid @RequestBody PhotoGalery photoGalery, HttpServletResponse response) {
		PhotoGalery savedPhotoGalery = photoGaleryRepository.save(photoGalery);
		
		//Lança o evento para retornar a URI no Header de retorno da requisição
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedPhotoGalery.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPhotoGalery); 
	}  
	
	@GetMapping("/{id}")  
	@PreAuthorize("hasAuthority('ROLE_SEARCH_PHOTO_GALERY') and #oauth2.hasScope('read')")
	public ResponseEntity<PhotoGalery> getById(@PathVariable Integer id) {
		PhotoGalery foundPhotoGalery = photoGaleryRepository.findOne(id);
		return foundPhotoGalery ==null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(foundPhotoGalery);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_DELETE_PHOTO_GALERY') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		photoGaleryRepository.delete(id);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_UPDATE_PHOTO_GALERY') and #oauth2.hasScope('write')")
	public ResponseEntity<PhotoGalery> update(@PathVariable Integer id, @Valid @RequestBody PhotoGalery photoGalery){
		PhotoGalery savedPhotoGalery = photoGaleryService.update(id, photoGalery);
		return ResponseEntity.ok(savedPhotoGalery); 
	}
}
