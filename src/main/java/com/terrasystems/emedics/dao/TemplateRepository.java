package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.enums.TypeEnum;
import com.terrasystems.emedics.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, String> {
    List<Template> findByTypeEnum(TypeEnum type);
}
