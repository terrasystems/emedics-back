package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.UserTemplate;
import com.terrasystems.emedics.model.dto.DashboardTemplateResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.TemplateDto;

import java.util.List;

public interface TemplateService {
    List<UserTemplate> getAllUserTemplates();
    StateDto deleteUserTemplateById(String id);

    List<TemplateDto> getAllTemplates();
    StateDto payTemplate(String id);
    DashboardTemplateResponse loadTemplate(String id);
    Template previewTemplate(String id);
}
