package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.model.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, String> {
    List<Patient> findByNameContainingOrEmailContaining(String name, String email);
}
