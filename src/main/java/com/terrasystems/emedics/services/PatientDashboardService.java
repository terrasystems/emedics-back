package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.UserFormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.UserForm;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.UserFormDto;
import com.terrasystems.emedics.model.dto.ListDashboardUserFormsResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.NEVER)
public class PatientDashboardService implements DashboardService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFormRepository userFormRepository;

    @Override
    @Transactional
    public List<UserForm> getAllUserForms() {
        Patient currentUser = (Patient) SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<UserForm> userForms = currentUser.getUserForms();
        return userForms;
    }

    @Override
    @Transactional
    public UserForm getUserFormById(String id) {
        UserForm userForm = userFormRepository.findOne(id);
        return userForm;
    }

    @Override
    @Transactional
    public UserForm editUserForm(UserFormDto userFormDto) {
        UserForm userForm = userFormRepository.findOne(userFormDto.getId());
        userForm.setData(userFormDto.getData().toString());
        userForm = userFormRepository.save(userForm);
        return userForm;
    }


    @Override
    public ListDashboardUserFormsResponse changeActiveUserForms(Set<String> newActiveUserForms) {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());
        ListDashboardUserFormsResponse response = new ListDashboardUserFormsResponse();
        if (newActiveUserForms.size() > patient.getAllowedFormsCount()){
            response.setState(new StateDto(false, "U can't change more forms then u permitted"));
            return response;
        }
        else {

            List<UserForm> list = patient.getUserForms();
            List<UserForm> newList = list.stream()
                    .map((item) -> {
                        if (newActiveUserForms.contains(item.getId())) {
                            item.setActive(true);
                            userFormRepository.save(item);
                            item.setBlank(null);
                        } else {
                            if (item.isActive()) {
                                item.setActive(false);
                                userFormRepository.save(item);
                            }
                        }
                        return item;
                    })
                    .filter(item -> item.isActive())
                    .collect(Collectors.toList());
            System.out.println(patient.getEmail());
            response.setState(new StateDto(true, "Active forms changed"));
            return response;
        }
    }

    @Override
    //@Transactional
    public List<UserForm> getActiveUserForms() {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());
        return (List<UserForm>) userFormRepository.findByUserAndActive(patient.getId());
    }
}
