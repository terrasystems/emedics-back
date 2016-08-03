package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.enums.FormEnum;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.UserTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.integration.dispatcher.LoadBalancingStrategy;

import java.util.List;

public interface UserTemplateRepository extends JpaRepository<UserTemplate, String> {
    Long countByTypeAndUser_Id(String type, String id);

    List<UserTemplate> findByTypeAndUser_Id(String type, String userId);
    Long countByTemplate_IdAndUser_Id(String templateId, String userId);
    List<UserTemplate> findByUser_Id(String userId);
    Long countByUser_IdAndTemplate_CommercialEnumIsFalse(String id);
    UserTemplate findByUser_IdAndTemplate_Id(String userId, String templateId);
    List<UserTemplate> findByUserAndTemplate_NameContainingIgnoreCase(User user, String name);
}