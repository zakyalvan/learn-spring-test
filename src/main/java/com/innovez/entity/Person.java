package com.innovez.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="innvz_person")
@SuppressWarnings("serial")
public class Person implements Serializable {
	@Id
	@Email
	@Column(name="email")
	private String email;
	
	@NotNull
	@Column(name="name")
	private String name;
	
	@NotNull
	@Column(name="password")
	private String password;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
