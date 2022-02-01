package br.com.framework.api.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.framework.api.model.Person;
import br.com.framework.api.repository.PersonRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private PersonRepository personRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Person> personOptional = personRepository.findByLogin(login);
		Person person = personOptional.orElseThrow(()-> new UsernameNotFoundException("Login e/ou senha incorretos"));
		return new User(login, person.getPassword(), getRoles(person));
	}
	
	private Collection<? extends GrantedAuthority> getRoles(Person person){
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		person.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getDescription())));
		return authorities;
	}
}
