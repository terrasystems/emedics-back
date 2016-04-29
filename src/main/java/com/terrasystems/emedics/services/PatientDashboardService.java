package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.NEVER)
public class PatientDashboardService implements DashboardService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FormRepository formRepository;

    @Override
    @Transactional
    public List<Form> getAllForms() {
        Patient currentUser = (Patient) SecurityContextHolder.getContext().getAuthentication().getDetails();

        List<Form> forms = currentUser.getForms();
        return forms;
    }

    @Override
    public List<Form> changeActiveForms(Set<String> newActiveForms) {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());

        //TODO change it with query
        List<Form> list = patient.getForms();
        List<Form> newList = list.stream()
                .map((item) -> {
                    if (newActiveForms.contains(item.getId())){
                        item.setActive(true);
                    } else {
                        item.setActive(false);
                    }
                    return item;
                })
                .filter(item -> item.isActive())
                .collect(Collectors.toList());
        return newList;
    }

    @Override
    @Transactional
    public List<Form> getActiveForms() {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());
        List<Form> forms = patient.getForms().stream()
                .filter(item -> item.isActive())
                .collect(Collectors.toList());
        return forms;
    }
}
