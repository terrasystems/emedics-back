package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.BlankRepository;
import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Blank;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.ListDashboardFormsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFormsDashboardService {
    @Autowired
    FormRepository formRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BlankRepository blankRepository;

    public void generateFormsForUser(String email) {
        User loadedUser = userRepository.findByEmail(email);
        List<Form> userForms = new ArrayList<>();
        List<Blank> blankList = (List<Blank>) blankRepository.findAll();
         blankList.stream().forEach((item) -> {
             Form form = new Form();
             form.setBlank(item);
             userForms.add(form);
         });
        loadedUser.setForms(userForms);
        userRepository.save(loadedUser);

    }

}
