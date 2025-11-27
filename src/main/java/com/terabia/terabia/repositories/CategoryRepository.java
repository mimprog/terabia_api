package com.terabia.terabia.repositories;

import com.terabia.terabia.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository <Category, Integer> {

    Optional <Category> findByName(String parentName);
}
