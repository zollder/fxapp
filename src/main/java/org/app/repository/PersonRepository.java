package org.app.repository;

import java.util.List;

import org.app.model.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Implements Repository for {@link Person} entities.
 */
@Repository("personRepository")
public interface PersonRepository extends CrudRepository<Person, Long>
{
	/** Loads {@link Person} by name. */
	public List<Person> findByName(String name);

	/** Loads given {@link User} by email. */
	@Query("MATCH (person:Person) WHERE person.city ={city} RETURN person")
	public Person findByCity(String city);
}