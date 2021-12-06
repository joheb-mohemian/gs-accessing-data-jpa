/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.accessingdatajpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTests {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private CustomerRepository customers;

	private Customer customer;

	@BeforeEach
	void setUpData() {
		customer = new Customer("first", "last");
		Address address = new Address(customer, "street");
		customer.setAddress(address);
		entityManager.persist(address);
		entityManager.persist(customer);
	}

	@Test
	void testEntityWithOneToOne() {
		Customer customerEntity = customers.findById(customer.getId().longValue());
		assertThat(customerEntity.getAddress()).isNotNull();
	}

	@Test
	void testProjectionWithOneToOne() {
		CustomerProjection customerProjection = customers.findById(customer.getId(), CustomerProjection.class);
		final Address address = customerProjection.getAddress();
		assertThat(address).isNotNull();
	}

	@Test
	void testCustomerQuery(){
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
		query.from(Customer.class);
		final Customer customer = entityManager.createQuery(query).getSingleResult();

		assertThat(customer).isNotNull();
		assertThat(customer.getAddress()).isNotNull();
	}
	@Test
	void testTupleQuery(){
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
		final Root<Customer> root = query.from(Customer.class);
		query.multiselect(root.get("id"),root.get("address"));
		final Tuple tuple = entityManager.createQuery(query).getSingleResult();

		assertThat(tuple).isNotNull();
		assertThat(tuple.get(0)).isEqualTo(customer.getId());
		assertThat(tuple.get(1)).describedAs("address should be pressent").isNotNull().isInstanceOf(Address.class);
	}
}
