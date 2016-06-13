package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.dao.UserTemplateRepository;
import com.terrasystems.emedics.enums.FormEnum;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.UserTemplate;
import com.terrasystems.emedics.model.dto.DashboardTemplateResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.TemplateMapper;
import com.terrasystems.emedics.model.mapping.UserTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService, CurrentUserService{

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    UserTemplateRepository userTemplateRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Template> getAllTemplates() {
        List<Template> templates = (List) templateRepository.findAll();
        return templates;
    }

    @Override
    public List<UserTemplate> getAllUserTemplates() {
        User currentUser = userRepository.findByEmail(getPrincipals());
        List<UserTemplate> userTemplates = currentUser.getUserTemplates();
        return userTemplates;
    }

    @Override
    @Transactional
    public StateDto deleteUserTemplateById(String id) {
        User currentUser = userRepository.findByEmail(getPrincipals());
        UserTemplate userTemplate = userTemplateRepository.findOne(id);
        if(userTemplate != null) {
            currentUser.getUserTemplates().remove(userTemplate);
            userRepository.save(currentUser);
            StateDto state = new StateDto();
            state.setValue(true);
            state.setMessage("User template deleted");
            return state;
        } else {
            StateDto state = new StateDto();
            state.setValue(false);
            state.setMessage("User template with such id doesn't exist");
            return state;
        }

    }

    @Override
    @Transactional
    public StateDto payTemplate(String id) {
        User currentUser = userRepository.findByEmail(getPrincipals());
        Template template = templateRepository.findOne(id);
        if(template != null) {
            UserTemplate userTemplate = new UserTemplate();
            userTemplate.setType(FormEnum.PAID.toString());
            userTemplate.setDescription(template.getDescr());
            userTemplate.setTemplate(template);
            userTemplate.setUser(currentUser);
            List<UserTemplate> userTemplates = currentUser.getUserTemplates();
            userTemplates.add(userTemplate);
            userTemplateRepository.save(userTemplate);
            userRepository.save(currentUser);
            StateDto state = new StateDto();
            state.setValue(true);
            state.setMessage("Template paid");
            return state;
        } else {
            StateDto state = new StateDto();
            state.setValue(false);
            state.setMessage("Template with such id doesn't exist");
            return state;
        }
    }

    @Override
    @Transactional
    public DashboardTemplateResponse loadTemplate(String id) {
        DashboardTemplateResponse response = new DashboardTemplateResponse();
        User currentUser = userRepository.findByEmail(getPrincipals());
        Template template = templateRepository.findOne(id);
        if (userTemplateRepository.countByTemplate_IdAndUser_Id(template.getId(),currentUser.getId()) > 0) {
            StateDto state = new StateDto();
            state.setValue(true);
            state.setMessage("Template loaded");
            response.setState(state);
            response.setResult(userTemplateRepository.findByTemplate_IdAndUser_Id(template.getId(), currentUser.getId()).getId());
            return response;
        } else {
            long count = userTemplateRepository.countByTypeAndUser_Id(FormEnum.FREE.name(),currentUser.getId());
            if(count < 5) {
                if(template != null) {
                    UserTemplate userTemplate = new UserTemplate();
                    userTemplate.setType(FormEnum.FREE.toString());
                    userTemplate.setDescription(template.getDescr());
                    userTemplate.setTemplate(template);
                    userTemplate.setUser(currentUser);
                    List<UserTemplate> userTemplates = currentUser.getUserTemplates();
                    userTemplates.add(userTemplate);
                    userTemplateRepository.save(userTemplate);
                    userRepository.save(currentUser);
                    StateDto state = new StateDto();
                    state.setValue(true);
                    state.setMessage("Template loaded");
                    response.setState(state);
                    response.setResult(userTemplate.getId());

                    return response;
                } else {
                    StateDto state = new StateDto();
                    state.setValue(false);
                    state.setMessage("Template with such id doesn't exist");
                    response.setState(state);
                    return response;
                }
            } else {
                StateDto state = new StateDto();
                state.setValue(false);
                state.setMessage("You can't load more than 5 Templates");
                response.setState(state);
                return response;
            }
        }
    }

    @Override
    public Template previewTemplate(String id) {
        Template template = templateRepository.findOne(id);
        return template;
    }

    public StateDto giftTemplate(String templateId, String patientId) {
        Template template = templateRepository.findOne(templateId);
        if (userTemplateRepository.countByTemplate_Id(template.getId()) == 0) {
            Patient patient = (Patient) userRepository.findOne(patientId);
        }

        return null;
    }


}
