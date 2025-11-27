package com.terabia.terabia.repositories;


import com.terabia.terabia.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, Integer> {

    List <Product> findAllByProductCategory_Name(String category);
    Optional<Product> findByName(String name);
}
