package com.terabia.terabia.services;

import com.terabia.terabia.models.Customer;
import com.terabia.terabia.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List <Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Integer id) {
        return Optional.ofNullable(customerRepository.findById(id).orElse(null));
    }

}
