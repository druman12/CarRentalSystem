package com.CustomerService.service;
import com.CustomerService.Repo.CustomerRepo;
import com.CustomerService.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }


    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Integer id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(existing -> {
                    existing.setFullName(updatedCustomer.getFullName());
                    existing.setPhoneNumber(updatedCustomer.getPhoneNumber());
                    existing.setAddress(updatedCustomer.getAddress());
                    existing.setDrivingLicenseNumber(updatedCustomer.getDrivingLicenseNumber());
                    return customerRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}

