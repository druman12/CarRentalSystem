package com.CustomerService.service;

import com.CustomerService.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Optional<Customer> getCustomerById(Integer id);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Integer id, Customer updatedCustomer);

    void deleteCustomer(Integer id);
}

