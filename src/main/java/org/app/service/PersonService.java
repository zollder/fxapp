package org.app.service;

import java.util.List;

import org.app.model.Person;

/**
 * Implements {@link Person} service.
 */
public interface PersonService
{
	/** Loads all {@link Person} entities. */
	public List<Person> findAll();

	/** Saves (inserts) given {@link Person} entity. Returns saved entity. */
	public Person saveOrUpdate(Person entity);

	/**
	 * Deletes {@link Person} entity by specified ID.
	 * Returns true if delete operation is successful.
	 * Returns false otherwise
	 */
	public boolean delete(Long id);
}