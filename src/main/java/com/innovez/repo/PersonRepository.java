package com.innovez.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.entity.Person;

public interface PersonRepository extends JpaRepository<Person, String> {

}
