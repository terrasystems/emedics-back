package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.DocType;
import org.springframework.data.repository.CrudRepository;

public interface DocTypeRepository extends CrudRepository<DocType, String> {
}
