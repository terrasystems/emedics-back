package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.enums.DocTypeEnum;
import com.terrasystems.emedics.model.DocType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocTypeRepository extends JpaRepository<DocType, String> {
    List<DocType> findByValue(DocTypeEnum docTypeEnum);
}
