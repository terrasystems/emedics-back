package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;


public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    User findByName(String name);
    User findById(String id);
    User findByEmail(String email);
    User findByValueKey(String valueKey);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM User c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") String email);
    List<User> findAll(Iterable<String> ids);
    User findByActivationToken(String link);

    List<User> findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(String id, UserType userType, String name, String id2, UserType userType2, String email, String id3, UserType userType3, String type);









            /*findByIdIsNotAndNameContainingIgnoreCaseOrType_NameContainingIgnoreCaseOrEmailContainingIgnoreCase*/
}
