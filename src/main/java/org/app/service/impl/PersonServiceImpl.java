package org.app.service.impl;

import java.time.LocalDate;
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
				new Person(null, "Hans", "Muster", "123 Street", 13579, "Berlin", LocalDate.of(1980, 5, 10)),
				new Person(null, "Ruth", "Mueller", "236 Street", 10298, "Berlin", LocalDate.of(2000, 5, 10)),
				new Person(null, "Heinz", "Kurz", "432 Street", 34256, "Berlin", LocalDate.of(1987, 5, 10)),
				new Person(null, "Cornelia", "Meier", "654 Street", 19387, "Berlin", LocalDate.of(1978, 5, 10)),
				new Person(null, "Werner", "Meyer", "378 Street", 98081, "Berlin", LocalDate.of(1998, 5, 10)),
				new Person(null, "Lydia", "Kunz", "274 Street", 19902, "Berlin", LocalDate.of(1975, 5, 10)),
				new Person(null, "Anna", "Best", "920 Street", 13579, "Berlin", LocalDate.of(1994, 5, 10)),
				new Person(null, "Stefan", "Meier", "111 Street", 11223, "Berlin", LocalDate.of(2001, 5, 10)),
				new Person(null, "Martin", "Mueller", "777 Street", 10009, "Berlin", LocalDate.of(2000, 5, 10)));

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
