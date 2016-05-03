package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.FormDto;
import com.terrasystems.emedics.model.dto.ListDashboardFormsResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.SourceExtractor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
    public List<Form> getFormById(String id) {

        List<Form> forms = new ArrayList<>();
        forms.add(formRepository.findOne(id));

        return forms;
    }

    /*@Override
    @Transactional
    public Form editForm(FormDto formDto) {
        Form form = formRepository.findOne(formDto.getId());
        form.setData(formDto.getData);
        formRepository.save(form);
    }
    */


    @Override
    public ListDashboardFormsResponse changeActiveForms(Set<String> newActiveForms) {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());
        ListDashboardFormsResponse response = new ListDashboardFormsResponse();
        if (newActiveForms.size() > patient.getAllowedFormsCount()){
            response.setState(new StateDto(false, "U can't change more forms then u permitted"));
            return response;
        }
        else {

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
    public List<Form> getActiveForms() {
        Patient patient = (Patient) userRepository.findByEmail(getPrincipals());
        /*List<Form> forms = patient.getForms().stream()
                .filter(item -> item.isActive())
                .collect(Collectors.toList());*/

        return (List<Form>) formRepository.findByUserAndActive(patient.getId());
    }
}
