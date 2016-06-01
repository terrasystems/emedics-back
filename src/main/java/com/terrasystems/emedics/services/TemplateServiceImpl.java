package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService, CurrentUserService{

    @Autowired
    TemplateRepository templateRepository;

    @Override
    public List<Template> getAllTemplates() {
        List<Template> templates = (List) templateRepository.findAll();
        return templates;
    }
}
