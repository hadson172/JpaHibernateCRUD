package crudapp.dao;

import crudapp.model.Customer;

import java.util.Optional;

public interface CustomerDAO {

    boolean saveCustomerIfNotExists(Customer customer);

    Optional<Customer> getCustomerByUsername(String username);

    void updateCustomer(Customer customer);

    boolean removeCustomer(String username);

    boolean containsCustomer(String username);

}
