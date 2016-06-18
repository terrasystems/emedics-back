package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface DoctorRepository extends CrudRepository<Doctor,String> {
    List<Doctor> findByIdIsNotAndNameContainingOrType_NameContainingOrEmailContaining(String id, String name, String type, String email);
    @Query("select d from Doctor d left join d.type dt " +
            "where (d.id != :id) and ((lower(d.email) like :search) or (lower(d.name) " +
            "like :search) or (lower(d.orgName) like :search) or (lower(dt.name) like :search) ) ")
    //TODO search in lower case
    List<Doctor> findByIdIsNot(@Param("search") String search, @Param("id") String id);
    //List<Doctor> findByIdIsNotAndNameContainingOrIdIsNotAndType_NameContainingOrEmailContaining(String id, String name, String type, String email);
}
