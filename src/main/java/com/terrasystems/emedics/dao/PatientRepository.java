package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, String> {
    List<Patient> findByIdIsNotAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String id, String name, String email);

   /* @Query(value = "select p, h.date, uf.id, uf.blank.name from Patient p left join UserForm uf on p.id = uf.user.id inner join History h on uf.id = h.userForm.id where p.id = :id")
    @Query(value = "select p from Patient p ")
    List<Patient> findPatient(@Param(value = "id") String docid);*/
}
