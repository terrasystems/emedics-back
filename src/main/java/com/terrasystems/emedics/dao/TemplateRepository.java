package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.enums.TypeEnum;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.mapping.TypeMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, String> {
    List<Template> findByTypeEnum(TypeEnum type);
    List<Template> findByNameContainingIgnoreCase(String name);
    List<Template> findByNameContainingIgnoreCaseAndTypeEnum(String name, TypeEnum typeEnum);
}
