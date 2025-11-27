package com.terabia.terabia.repositories;

import com.terabia.terabia.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order, Integer> {

    boolean existsByCustomerId(Integer customerId);
}
