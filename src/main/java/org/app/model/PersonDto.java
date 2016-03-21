package org.app.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonDto
{
	private LongProperty id;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty street;
	private IntegerProperty postalCode;
	private StringProperty city;
	private ObjectProperty<LocalDate> birthday;

	//--------------------------------------------------------------------------------------
	public PersonDto() { this(new Long(0), null, null); }

	public PersonDto(Person person)
	{
		this(person.getId(), person.getFirstName(), person.getLastName());
	}

	public PersonDto(Long id, String fname, String lname)
	{
		this.id = new SimpleLongProperty(id);
		this.firstName = new SimpleStringProperty(fname);
		this.lastName = new SimpleStringProperty(lname);;
		this.street = new SimpleStringProperty("some street");
		this.postalCode = new SimpleIntegerProperty(123321);
		this.city = new SimpleStringProperty("some city");
		this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
	}

	//--------------------------------------------------------------------------------------
	public LongProperty idProperty()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id.set(id);
	}

	public Long getId()
	{
		return this.id.get();
	}

	//--------------------------------------------------------------------------------------
	public StringProperty firstNameProperty()
	{
		return firstName;
	}

	public void setFirstName(String fn)
	{
		this.firstName.set(fn);
	}

	public String getFirstName()
	{
		return this.firstName.get();
	}

	//--------------------------------------------------------------------------------------
	public StringProperty lastNameProperty()
	{
		return lastName;
	}

	public String getLastName()
	{
		return lastName.get();
	}

	public void setLastName(String ln)
	{
		this.lastName.set(ln);
	}

	//--------------------------------------------------------------------------------------
	public void streetProperty(StringProperty street)
	{
		this.street = street;
	}

	public String getStreet()
	{
		return street.get();
	}

	public void setStreet(String street)
	{
		this.street.set(street);;
	}

	//--------------------------------------------------------------------------------------
	public void postalCodeProperty(IntegerProperty postalCode)
	{
		this.postalCode = postalCode;
	}

	public Integer getPostalCode()
	{
		return postalCode.get();
	}

	public void setPostalCode(Integer pc)
	{
		this.postalCode.set(pc);
	}

	//--------------------------------------------------------------------------------------
	public void cityProperty(StringProperty city)
	{
		this.city = city;
	}

	public String getCity()
	{
		return city.get();
	}

	public void setCity(String city)
	{
		this.city.set(city);
	}

	//--------------------------------------------------------------------------------------
	public void birthdayProperty(ObjectProperty<LocalDate> birthday)
	{
		this.birthday = birthday;
	}

	public LocalDate getBirthday()
	{
		return birthday.get();
	}

	public void setBirthday(LocalDate bd)
	{
		this.birthday.set(bd);
	}
}