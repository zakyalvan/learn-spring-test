package com.innovez.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.innovez.entity.Person;

/**
 * 
 * @author zakyalvan
 *
 */
@SuppressWarnings("serial")
public final class UserDetailsAdapter implements UserDetails {
	private final Person person;
	private final List<GrantedAuthority> authorities;
	
	public UserDetailsAdapter(Person adaptee) {
		Assert.notNull(adaptee, "Adapted person object should not be null.");
		person = adaptee;
		authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	@Override
	public String getUsername() {
		return person.getEmail();
	}
	@Override
	public String getPassword() {
		return person.getPassword();
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
