package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Form;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FormRepository extends CrudRepository<Form, String> {
    @Query("select f from Form as f where f.user.id=:userId and f.active=true")
    Iterable<Form> findByUserAndActive(@Param("userId") String userId);
}
