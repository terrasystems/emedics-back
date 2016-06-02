package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.dao.UserTemplateRepository;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.UserTemplate;
import com.terrasystems.emedics.model.dto.StateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserTemplateServiceImpl implements UserTemplateService, CurrentUserService {

    @Autowired
    UserTemplateRepository userTemplateRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserTemplate> getAllTemplates() {
        User currentUser = userRepository.findByEmail(getPrincipals());
        List<UserTemplate> userTemplates = currentUser.getUserTemplates();
        return userTemplates;
    }

    @Override
    public StateDto deleteUserTemplateById(String id) {
        User currentUser = userRepository.findByEmail(getPrincipals());
        UserTemplate userTemplate = userTemplateRepository.findOne(id);
        currentUser.getUserTemplates().remove(userTemplate);
        userRepository.save(currentUser);
        StateDto state = new StateDto();
        state.setValue(true);
        state.setMessage("User template deleted");
        return state;
    }
}