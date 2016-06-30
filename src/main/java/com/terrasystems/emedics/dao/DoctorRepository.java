package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface DoctorRepository extends JpaRepository<Doctor,String> {
    List<Doctor> findByIdIsNotAndNameContainingIgnoreCaseOrType_NameContainingIgnoreCaseOrEmailContainingIgnoreCase(String id, String name, String type, String email);
    @Query("select d from Doctor d left join d.type dt " +
            "where (d.id != :id) and ((lower(d.email) like :search) or (lower(d.name) " +
            "like :search) or (lower(d.orgName) like :search) or (lower(dt.name) like :search) ) ")
    //TODO search in lower case
    List<Doctor> findByIdIsNot(@Param("search") String search, @Param("id") String id);
    //List<Doctor> findByIdIsNotAndNameContainingOrIdIsNotAndType_NameContainingOrEmailContaining(String id, String name, String type, String email);
    /*@Query("select d from Doctor d left join d.type dt " +
            "where d.id != :id and (lower(d.email) like :search or lower(d.name) " +
            "like :search or lower(d.orgName) like :search or lower(dt.name) like :search) ")
    List<Doctor> findByIdIsNot(@Param("search") String search, @Param("id") String id);*/
    List<Doctor> findByIdIsNotAndNameContainingIgnoreCaseOrIdIsNotAndEmailContainingIgnoreCaseOrIdIsNotAndType_NameContainingIgnoreCase(String id1, String name, String id2, String email, String id3, String type);
}
