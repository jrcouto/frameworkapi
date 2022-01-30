package br.com.framework.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.framework.api.model.Person;
import br.com.framework.api.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	public Person update(Integer id, Person post) {
		Person savedPerson = personRepository.findOne(id);
		if(savedPerson==null) {
			throw new EmptyResultDataAccessException(1);
		} 
		BeanUtils.copyProperties(post, savedPerson, "id");
		return personRepository.save(savedPerson);
	}
}
