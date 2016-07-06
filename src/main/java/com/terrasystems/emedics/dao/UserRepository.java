package com.terrasystems.emedics.dao;

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
    User findByEmail(String email);
    User findByValueKey(String valueKey);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM User c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") String email);
    List<User> findAll(Iterable<String> ids);
    Set<User> findByIdIsNotAndNameContainingOrEmailContaining(String name, String email, String id);
    User findByActivationToken(String link);

    @Query("select d from User d "+//" left join DocType dt " +
            "where (d.id != :id)")/* and ((lower(d.email) like :search) or (lower(d.name) " +
            "like :search) or (lower(d.orgName) like :search) or (lower(dt.name) like :search) ) ")*/
        //TODO search in lower case
    Set<User> findByIdIsNot(/*@Param("search") String search,*/ @Param("id") String id);
}
