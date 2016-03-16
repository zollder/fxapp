package org.app.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person
{
	private Long id;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty street;
	private IntegerProperty postalCode;
	private StringProperty city;
	private ObjectProperty<LocalDate> birthday;

	//--------------------------------------------------------------------------------------
	public Person()
	{
		this(null,null);
	}

	public Person(String fname, String lname)
	{
		this.firstName = new SimpleStringProperty(fname);
		this.lastName = new SimpleStringProperty(lname);;
		this.street = new SimpleStringProperty("some street");
		this.postalCode = new SimpleIntegerProperty(123321);
		this.city = new SimpleStringProperty("some city");
		this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
	}

	//--------------------------------------------------------------------------------------
	public Long getId()
	{
		return this.id;
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