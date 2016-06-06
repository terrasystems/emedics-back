package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.enums.FormEnum;
import com.terrasystems.emedics.model.UserTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserTemplateRepository extends CrudRepository<UserTemplate, String> {
    Long countByTypeAndUser_Id(String type, String id);
}