package com.example.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Customer findById(long id);

	<T> T findById(long id, Class<T> type);
}
