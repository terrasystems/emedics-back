package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor,String> {
    List<Doctor> findByNameContainingOrTypeContainingOrEmailContaining(String name, String type, String email);
}
