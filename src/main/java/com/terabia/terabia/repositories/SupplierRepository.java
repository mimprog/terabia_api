package com.terabia.terabia.repositories;

import com.terabia.terabia.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository <Supplier, Integer> {
}
