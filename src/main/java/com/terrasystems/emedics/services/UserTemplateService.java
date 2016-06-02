package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.UserTemplate;
import com.terrasystems.emedics.model.dto.StateDto;
import java.util.List;

public interface UserTemplateService {
    List<UserTemplate> getAllTemplates();
    StateDto deleteUserTemplateById(String id);
}
