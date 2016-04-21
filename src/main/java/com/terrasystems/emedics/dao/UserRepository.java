package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by tester on 20.04.16.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String name);
    User findByEmail(String email);
}
