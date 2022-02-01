package br.com.framework.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
	
	public PhotoGalery updatePhotoList(Integer id, String caminhoFoto) {
		PhotoGalery savedPhotoGalery = photoGaleryRepository.findOne(id);
				
		if(savedPhotoGalery==null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		savedPhotoGalery.getPhotoList().add(caminhoFoto);
		

		return photoGaleryRepository.save(savedPhotoGalery);
	}

}
