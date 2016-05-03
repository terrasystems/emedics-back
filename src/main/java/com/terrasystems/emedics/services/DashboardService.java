package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.dto.FormDto;
import com.terrasystems.emedics.model.dto.ListDashboardFormsResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;


public interface DashboardService {
    List<Form> getAllForms();
    ListDashboardFormsResponse changeActiveForms(Set<String> newActiveForms);
    List<Form> getActiveForms();
    Form getFormById(String id);
    Form editForm(FormDto formDto);
    default String getPrincipals() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

