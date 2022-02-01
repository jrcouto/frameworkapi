package br.com.framework.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.framework.api.model.Person;
import br.com.framework.api.model.PhotoGalery;
import br.com.framework.api.repository.PhotoGaleryRepository;

@Service
public class PhotoGaleryService {
	
	@Autowired
	private PhotoGaleryRepository photoGaleryRepository;
	
	public PhotoGalery update(Integer id, PhotoGalery photoGalery) {
		PhotoGalery savedPhotoGalery = photoGaleryRepository.findOne(id);
		if(savedPhotoGalery==null) {
			throw new EmptyResultDataAccessException(1);
		} 
		
		BeanUtils.copyProperties(photoGalery, savedPhotoGalery, "id");
		return photoGaleryRepository.save(savedPhotoGalery);
	}
	
	/*
	 * public List<PhotoGalery> getGaleriesByPersonId(Integer personId) { Person p =
	 * new Person(); p.setId(personId);
	 * 
	 * List<PhotoGalery> galeries = photoGaleryRepository.findByPerson(p);
	 * 
	 * if(galeries==null || galeries.isEmpty()) { throw new
	 * EmptyResultDataAccessException(1); }
	 * 
	 * return galeries; }
	 */
}
