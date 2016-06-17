package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface DoctorRepository extends CrudRepository<Doctor,String> {
    List<Doctor> findByIdIsNotAndNameContainingOrType_NameContainingOrEmailContaining(String id, String name, String type, String email);
    List<Doctor> findByIdIsNot(String id);
    List<Doctor> findByIdIsNotAndNameContainingOrIdIsNotAndType_NameContainingOrIdIsNotAndEmailContaining(String id, String name, String id2, String type, String id3, String email);
}
