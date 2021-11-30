package com.example.accessingdatajpa;

import javax.persistence.*;

@Entity
public class Address {

	@Id
	@Column(name = "customer_id")
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "customer_id")
	private Customer customer;

	protected Address() { }

	public Address(Customer customer, String street) {
		this.customer = customer;
		this.street = street;
	}

	private String street;

	public String getStreet() {
		return street;
	}
}
