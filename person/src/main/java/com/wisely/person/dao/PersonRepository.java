package com.wisely.person.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisely.person.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
