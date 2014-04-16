package com.innovez.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.innovez.entity.Person;
import com.innovez.repo.PersonRepository;

/**
 * 
 * @author zakyalvan
 *
 */
public class UserDetailsServiceAdapter implements UserDetailsService {
	private PersonRepository personRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(personRepository.exists(username)) {
			throw new UsernameNotFoundException(String.format("Username %s not found is user database", username));
		}
		
		Person person = personRepository.findOne(username);
		return new UserDetailsAdapter(person);
	}

	@Autowired
	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
}
