package br.com.framework.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.framework.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{

}
