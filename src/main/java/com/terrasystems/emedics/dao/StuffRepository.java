package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Stuff;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StuffRepository extends CrudRepository<Stuff, String> {
    //TODO add search with join Organization for type
    //List<Stuff> findByNameContainingAndAdminIsTrueOrEmailContainingAndAdminIsTrue(String name, String email);
    List<Stuff> findByIdIsNotAndDoctor_NameContainingIgnoreCaseAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingIgnoreCaseAndDoctor_AdminIsTrue(String id, String fullname, String type);
    List<Stuff> findByIdIsNotAndDoctor_NameContainingIgnoreCaseAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingIgnoreCaseAndDoctor_AdminIsTrue(String id, String fullname, String type, Pageable pageable);
}
