package com.terabia.terabia.repositories;

import com.terabia.terabia.models.Product;
import com.terabia.terabia.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {


    List <ProductImage> findByProduct(Product product);
}
