package com.terabia.terabia.repositories;

import com.terabia.terabia.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {


    List <Stock> findAllByProductId(Integer productId);
    Optional <Stock> findByProductId(Integer productId);
}
