package com.wonderworcer.crudvaadin.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.wonderworcer.crudvaadin.model.Customer;
import com.wonderworcer.crudvaadin.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

@SpringComponent
@UIScope
public class CustomerService {
    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

    @Autowired
    private CustomerRepository repository;


    public void delete(Customer customer) {
        repository.delete(customer);
    }

    public void save (Customer customer)
    {
        repository.save(customer);
    }

}
