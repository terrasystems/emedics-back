package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DashboardService {
    List<Form> getAllForms();
    List<Form> changeActiveForms(List<Integer> newActiveForms);
    List<Form> getActiveForms();
    default String getPrincipals() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

