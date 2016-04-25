package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String name);
    User findByEmail(String email);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM User c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
