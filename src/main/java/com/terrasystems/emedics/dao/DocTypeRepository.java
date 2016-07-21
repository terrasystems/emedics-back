package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.enums.DocTypeEnum;
import com.terrasystems.emedics.model.Types;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocTypeRepository extends JpaRepository<Types, String> {
    List<Types> findByValue(DocTypeEnum docTypeEnum);
}
