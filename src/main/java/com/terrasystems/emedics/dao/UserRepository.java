package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String name);
    User findByEmail(String email);
}
