package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.ListDashboardFormsResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.SourceExtractor;
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
    public ListDashboardFormsResponse getAllForms() {
        Patient currentUser = (Patient) SecurityContextHolder.getContext().getAuthentication().getDetails();

        List<Form> forms = currentUser.getForms();
        forms.stream().map((item) -> {
            item.setBody(null);
            item.setDescr(item.getBlank().getDescr());
            item.setName(item.getBlank().getName());
            item.setNumber(item.getBlank().getNumber());
            item.setBlank(null);
            return item;
        })
                .collect(Collectors.toList());
        System.out.println(currentUser.getEmail());
        ListDashboardFormsResponse response = new ListDashboardFormsResponse();
        response.setState(new StateDto(true, "All forms"));
        response.setList(forms);
        return response;
    }

    @Override
    public ListDashboardFormsResponse changeActiveForms(Set<String> newActiveForms) {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());
        ListDashboardFormsResponse response = new ListDashboardFormsResponse();
        if (newActiveForms.size() > patient.getAllowedFormsCount()){
            response.setState(new StateDto(false, "U can't change more forms then u permitted"));
            return response;
        }
        else {
            //TODO change it with query
            List<Form> list = patient.getForms();
            List<Form> newList = list.stream()
                    .map((item) -> {
                        if (newActiveForms.contains(item.getId())) {
                            item.setActive(true);
                            formRepository.save(item);
                            item.setBlank(null);
                        } else {
                            if (item.isActive()) {
                                item.setActive(false);
                                formRepository.save(item);
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
    public ListDashboardFormsResponse getActiveForms() {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());
        List<Form> forms = patient.getForms().stream()
                .filter(item -> item.isActive())
                .collect(Collectors.toList())
                .stream()
                .map((item) -> {
                    item.setBody("strange behavior");
                    item.setDescr(item.getBlank().getDescr());
                    item.setName(item.getBlank().getName());
                    item.setNumber(item.getBlank().getNumber());
                    item.setBlank(null);
                    return item;
                })
                .collect(Collectors.toList());
        ListDashboardFormsResponse response = new ListDashboardFormsResponse();
        response.setState(new StateDto(true, "Active forms"));
        response.setList(forms);
        return response;
    }
}
