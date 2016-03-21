package org.app.repository;

import org.app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Implements Repository for {@link Person} entities.
 */
@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long>
{}