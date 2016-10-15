package crudapp.dao;

import crudapp.model.Customer;

import java.util.Optional;

public interface CustomerDAO
{
    boolean saveCustomerIfNotExists(Customer customer);               //C
    Optional<Customer> getCustomerByUsername(String username);    //R
    void updateCustomer(Customer customer);             //U
    boolean removeCustomer(String username);            //D

    boolean containsCustomer(String username);

}
