package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Form;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;


public interface DashboardService {
    List<Form> getAllForms();
    List<Form> changeActiveForms(Set<String> newActiveForms);
    List<Form> getActiveForms();
    default String getPrincipals() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

