package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.UserTemplate;
import com.terrasystems.emedics.model.dto.StateDto;

import java.util.List;

public interface TemplateService {
    List<UserTemplate> getAllUserTemplates();
    StateDto deleteUserTemplateById(String id);

    List<Template> getAllTemplates();
    StateDto payTemplate(String id);
    StateDto loadTemplate(String id);
    Template previewTemplate(String id);
}
