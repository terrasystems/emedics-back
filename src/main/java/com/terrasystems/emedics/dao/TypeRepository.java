package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.enums.TypeEnum;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Types;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Types, String> {
    List<Types> findByUserType(UserType userType);
}
