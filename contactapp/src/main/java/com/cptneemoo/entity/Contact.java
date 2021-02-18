package com.cptneemoo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Contact")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank(message = "Name is mandatory")
	@Size(min = 2, max = 255, message 
    = "Name must be between 2 and 255 characters")
	private String name;
	
	@NotBlank(message = "Contact number is mandatory")
	@Size(min = 3, max = 32, message 
    = "Contact number must be between 3 and 32 characters")
	private String contactnumber;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContactnumber() {
		return contactnumber;
	}
	
	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	@Override
	public String toString() {
		return "Contact id=" + id + ", name=" + name + ", contactnumber=" + contactnumber;
	}
	
	
}
