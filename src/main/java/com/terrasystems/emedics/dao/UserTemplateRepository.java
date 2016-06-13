package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.enums.FormEnum;
import com.terrasystems.emedics.model.UserTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserTemplateRepository extends CrudRepository<UserTemplate, String> {
    Long countByTypeAndUser_Id(String type, String id);
    Long countByTemplate_Id(String templateId);
    List<UserTemplate> findByTypeAndUser_Id(String type, String userId);
    Long countByTemplate_IdAndUser_Id(String templateId, String userId);
    UserTemplate findByTemplate_IdAndUser_Id(String templateId, String userId);
}