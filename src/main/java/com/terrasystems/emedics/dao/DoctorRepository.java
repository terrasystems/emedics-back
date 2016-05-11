package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface DoctorRepository extends CrudRepository<Doctor,String> {
    Iterable<Doctor> findByUsernameContainingOrTypeContaining(String name, String type);
}
