package com.wonderworcer.crudvaadin;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.wonderworcer.crudvaadin.model.Customer;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@SpringComponent
@UIScope
public class CustomerService {

    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

    private final HashMap<Long, Customer> contacts = new HashMap<>();
    private long nextId = 0;

    private CustomerService() {
    }



    public synchronized List<Customer> findAll() {
        return findAll(null);
    }


    public synchronized List<Customer> findAll(String stringFilter) {
        ArrayList<Customer> arrayList = new ArrayList<>();
        for (Customer contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Customer>() {

            @Override
            public int compare(Customer o1, Customer o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }


    public synchronized List<Customer> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<Customer> arrayList = new ArrayList<>();
        for (Customer contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Customer>() {

            @Override
            public int compare(Customer o1, Customer o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    public synchronized long count() {
        return contacts.size();
    }


    public synchronized void delete(Customer value) {
        contacts.remove(value.getId());
    }


    public synchronized void save(Customer entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Customer is null.");
            return;
        }
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Customer) entry.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }

}