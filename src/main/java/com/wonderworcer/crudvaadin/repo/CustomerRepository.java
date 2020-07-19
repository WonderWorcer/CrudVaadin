package com.wonderworcer.crudvaadin.repo;

import com.wonderworcer.crudvaadin.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameStartsWithIgnoreCase(String name);

    Customer findById(long id);
}
