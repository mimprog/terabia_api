package com.terabia.terabia.repositories;

import com.terabia.terabia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {

    Optional<User> findByEmail(String email);
}
