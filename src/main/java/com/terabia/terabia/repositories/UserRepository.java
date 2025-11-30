package com.terabia.terabia.repositories;

import com.terabia.terabia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {

    Optional<User> findByEmail(String email);

    @Query(
            value = "SELECT * FROM users u WHERE " +
                    "u.firstname ILIKE %:query% OR " +
                    "u.lastname ILIKE %:query% OR " +
                    "u.email ILIKE %:query% OR " +
                    "u.phone ILIKE %:query%",
            nativeQuery = true
    )
    List<User> searchUsers(@Param("query") String query);

}
