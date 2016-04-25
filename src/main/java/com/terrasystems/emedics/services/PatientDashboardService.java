package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<Form> forms = (List<Form>) formRepository.findAll();
        return forms;
    }

    @Override
    public List<Form> changeActiveForms(List<Integer> newActiveForms) {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());


        return null;
    }

    @Override
    @Transactional
    public List<Form> getActiveForms() {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());
        return patient.getForms();
    }
}
