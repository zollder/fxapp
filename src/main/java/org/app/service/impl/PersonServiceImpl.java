package org.app.service.impl;

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

@Service("PersonService")
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
    	// TODO: add some data here
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
    	Person savedEntity = personRepository.save(person);
    	logger.error(String.format("Saved/updated a person. Person id: %s.", String.valueOf(savedEntity.getId())));
    	return savedEntity;
	}

    // ---------------------------------------------------------------------------------------------
	@Override
	@Transactional
	public void delete(Long id)
	{
		if ((id != null) && !personRepository.exists(id))
			personRepository.delete(id);
		else
			logger.error(String.format("A person with id %s doesn't exist.", String.valueOf(id)));
	}
}
