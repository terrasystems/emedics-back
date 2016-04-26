package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Form;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FormRepository extends CrudRepository<Form, Long> {
    Iterable<Form> findById(Iterable<Integer> ids);
}
