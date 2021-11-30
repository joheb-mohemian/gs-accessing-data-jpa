package com.example.accessingdatajpa;

public interface CustomerProjection {

	String getFirstName();

	String getLastName();

	AddressProjection getAddress();
}
