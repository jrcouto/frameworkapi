package br.com.framework.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.framework.api.model.PhotoGalery;

public interface PhotoGaleryRepository extends JpaRepository<PhotoGalery, Integer>{
 
	public List<PhotoGalery> findByPersonId(Integer id);
}
