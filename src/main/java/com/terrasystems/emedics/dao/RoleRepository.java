package com.terrasystems.emedics.dao;



import com.terrasystems.emedics.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

}
