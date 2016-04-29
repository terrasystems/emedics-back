package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.BlankRepository;
import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Blank;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
             form.setBody(item.getBody());
             form.setBlank(item);
             userForms.add(form);
         });
        loadedUser.setForms(userForms);
        userRepository.save(loadedUser);

    }
    @PostConstruct
    public void init() {
        List<Blank> blanks = new ArrayList<>();
        for (int i = 0; i<7; i++) {
            blanks.add(new Blank("one","two","three","fore"));
        }
        blankRepository.save(blanks);
        System.out.println("added blanks");
    }
}
