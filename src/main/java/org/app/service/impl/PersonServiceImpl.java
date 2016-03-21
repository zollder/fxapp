package org.app.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.app.model.Person;
import org.app.repository.PersonRepository;
import org.app.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("personService")
public class PersonServiceImpl implements PersonService
{
	protected static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	private PersonRepository personRepository;

	/**
	 * Add some data to avoid empty table at first load.
	 */
    @PostConstruct
    public void generateTestData() {
		List<Person> data = Arrays.asList(
				new Person("Hans", "Muster"),
				new Person("Ruth", "Mueller"),
				new Person("Heinz", "Kurz"),
				new Person("Cornelia", "Meier"),
				new Person("Werner", "Meyer"),
				new Person("Lydia", "Kunz"),
				new Person("Anna", "Best"),
				new Person("Stefan", "Meier"),
				new Person("Martin", "Mueller"));

    	List<Person> savedEntities = personRepository.save(data);
    	logger.info("Saved IDs: " + savedEntities.stream().map(Person::getId).collect(Collectors.toList()).toString());
    }

    // ---------------------------------------------------------------------------------------------
    @Override
	@Transactional(readOnly = true)
	public List<Person> findAll()
	{
    	Iterable<Person> it = personRepository.findAll();
    	return StreamSupport.stream(it.spliterator(), false).collect(Collectors.toList());
	}

    // ---------------------------------------------------------------------------------------------
    @Override
	@Transactional
	public Person saveOrUpdate(Person person)
	{
    	logger.info(String.format("Saving person with ID: %s", String.valueOf(person.getId())));
    	Person savedEntity = personRepository.save(person);
    	logger.info(String.format("Saved/updated a person. Person id: %s.", String.valueOf(savedEntity.getId())));
    	return savedEntity;
	}

    // ---------------------------------------------------------------------------------------------
	@Override
	@Transactional
	public boolean delete(Long id)
	{
		if ((id != null) && personRepository.exists(id)) {
			personRepository.delete(id);
			return true;
		}
		logger.error(String.format("A person with id %s doesn't exist.", String.valueOf(id)));
		return false;
	}
}
