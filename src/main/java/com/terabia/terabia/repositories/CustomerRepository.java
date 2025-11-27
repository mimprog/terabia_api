package com.terabia.terabia.repositories;

import com.terabia.terabia.models.Customer;
import com.terabia.terabia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByUser(User user);
}
