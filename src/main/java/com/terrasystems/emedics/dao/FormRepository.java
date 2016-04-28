package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Form;
import org.springframework.data.repository.CrudRepository;

public interface FormRepository extends CrudRepository<Form, Long> {
    Iterable<Form> findById(Iterable<Integer> ids);
}
