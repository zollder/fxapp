package org.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Person
{
	@Id
	@GeneratedValue
	private Long id;

	@Column private String firstName;
	@Column private String lastName;

	public Person() {}

	public Person(String fName, String lName)
	{
		this.firstName = fName;
		this.lastName = lName;
	}

	public Person(Long id, String fName, String lName)
	{
		this.id = id;
		this.firstName = fName;
		this.lastName = lName;
	}

	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String fname)
	{
		this.firstName = fname;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lname)
	{
		this.lastName = lname;
	}
}