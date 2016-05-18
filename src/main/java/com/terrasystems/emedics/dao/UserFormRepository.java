package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.UserForm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserFormRepository extends CrudRepository<UserForm, String> {
    @Query("select f from UserForm as f where f.user.id=:userId and f.active=true")
    Iterable<UserForm> findByUserAndActive(@Param("userId") String userId);
    UserForm findByUser_IdAndBlank_Id(String userId, String blankId);
}
