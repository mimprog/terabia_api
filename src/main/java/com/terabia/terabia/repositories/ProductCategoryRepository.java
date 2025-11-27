package com.terabia.terabia.repositories;

import com.terabia.terabia.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository <ProductCategory, Integer> {

    //Optional<ProductCategory> findByParentCategoryId(Integer parentCategoryId);
    Optional <ProductCategory> findByName(String name);
}