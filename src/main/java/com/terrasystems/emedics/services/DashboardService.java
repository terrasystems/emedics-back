package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.UserForm;
import com.terrasystems.emedics.model.dto.UserFormDto;
import com.terrasystems.emedics.model.dto.ListDashboardUserFormsResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;


public interface DashboardService {
    List<UserForm> getAllUserForms();
    ListDashboardUserFormsResponse changeActiveUserForms(Set<String> newActiveForms);
    List<UserForm> getActiveUserForms();
    UserForm getUserFormById(String id);
    UserForm editUserForm(UserFormDto userFormDto);
    default String getPrincipals() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

