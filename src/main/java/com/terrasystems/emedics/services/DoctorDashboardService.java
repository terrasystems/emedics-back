package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.UserFormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.UserForm;
import com.terrasystems.emedics.model.dto.ListDashboardUserFormsResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserFormDto;
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
public class DoctorDashboardService implements DashboardService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFormRepository userFormRepository;


    @Override
    @Transactional
    public List<UserForm> getAllUserForms() {
        Doctor currentUser = (Doctor) SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<UserForm> userForms = currentUser.getUserForms();
        return userForms;
    }

    @Override
    public ListDashboardUserFormsResponse changeActiveUserForms(Set<String> newActiveUserForms) {
        Doctor doctor = (Doctor) userRepository.findByEmail(getPrincipals());
        ListDashboardUserFormsResponse response = new ListDashboardUserFormsResponse();

        List<UserForm> list = doctor.getUserForms();
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
        System.out.println(doctor.getEmail());
        response.setState(new StateDto(true, MessageEnums.MSG_FORM_CHANGE.toString()));
        return response;
    }

    @Override
    public List<UserForm> getActiveUserForms() {
        Doctor doctor = (Doctor) userRepository.findByEmail(getPrincipals());
        return (List<UserForm>) userFormRepository.findByUserAndActive(doctor.getId());
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
}
