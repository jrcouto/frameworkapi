package br.com.framework.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.framework.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{
 
	public Optional<Person> findByLogin(String login);
}
