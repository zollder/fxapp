package org.app.model;

import java.time.LocalDate;

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
	@Column private String street;
	@Column private Integer postalCode;
	@Column private String city;
	@Column private LocalDate birthday;

	public Person() {}

	public Person(Long id, String fname, String lname, String street, Integer code, String city, LocalDate date)
	{
		setId(id);
		setFirstName(fname);
		setLastName(lname);
		setStreet(street);
		setPostalCode(code);
		setCity(city);
		setBirthday(date);
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public Integer getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(Integer postalCode)
	{
		this.postalCode = postalCode;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public LocalDate getBirthday()
	{
		return birthday;
	}

	public void setBirthday(LocalDate birthday)
	{
		this.birthday = birthday;
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