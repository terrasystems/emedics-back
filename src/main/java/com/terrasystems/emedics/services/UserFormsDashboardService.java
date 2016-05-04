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
import java.util.UUID;

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
             form.setUser(loadedUser);
             form.setData("blank.setDescr(item.getBlank().getDescr());\n" +
                     "                    blank.setName(item.getBlank().getName());\n" +
                     "                    blank.setBody(null);\n" +
                     "                    blank.setCategory(item.getBlank().getCategory());\n" +
                     "                    blank.setNumber(item.getBlank().getNumber());\n" +
                     "                    form.setBlank(blank);");
             userForms.add(form);
         });
        loadedUser.setForms(userForms);
        userRepository.save(loadedUser);

    }
    @PostConstruct
    public void init() {
        List<Blank> blanks = new ArrayList<>();
        for (int i = 0; i<7; i++) {

            blanks.add(new Blank("type","asd","{\"data\":[{\"key\":\"number\",\"type\":\"input\",\"templateOptions\":{\"type\":\"text\",\"label\":\"number\",\"placeholder\":\"\"}},{\"key\":\"FullName\",\"type\":\"input\",\"templateOptions\":{\"type\":\"text\",\"label\":\"Full name\",\"placeholder\":\"Enter Full name\"}},{\"key\":\"date1\",\"type\":\"datepicker\",\"templateOptions\":{\"label\":\"Date 1\",\"type\":\"text\",\"datepickerPopup\":\"dd-MMMM-yyyy\"}}]}", "asds", "asds", Integer.toString(i)));
        }
        blankRepository.save(blanks);
        System.out.println("added blanks");
    }
}
