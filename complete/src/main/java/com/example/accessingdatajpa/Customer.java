package com.example.accessingdatajpa;

import javax.persistence.*;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Address address;

	protected Customer() {}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%d, firstName='%s', lastName='%s', street='%s']",
				id, firstName, lastName, address != null ? address.getStreet() : "not set");
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
