package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.dto.ListDashboardFormsResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;


public interface DashboardService {
    ListDashboardFormsResponse getAllForms();
    ListDashboardFormsResponse changeActiveForms(Set<String> newActiveForms);
    ListDashboardFormsResponse getActiveForms();
    default String getPrincipals() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

