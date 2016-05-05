package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReferenceRepository extends CrudRepository<Reference, String>{
    List<Reference> findByUser(User user);
}
