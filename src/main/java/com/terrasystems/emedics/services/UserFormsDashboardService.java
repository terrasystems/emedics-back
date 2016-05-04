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
             //form.setData("{\"FullName\": \"\", \"number\": \"\", \"date1\": null, \"sex\": \"-1\"}");
             userForms.add(form);
         });
        loadedUser.setForms(userForms);
        userRepository.save(loadedUser);

    }
    @PostConstruct
    public void init() {
        List<Blank> blanks = new ArrayList<>();
        for (int i = 0; i<7; i++) {

            blanks.add(new Blank("type","asd","[\n" +
                    "{\n" +
                    "\"key\": \"number\",\n" +
                    "\"type\": \"input\",\n" +
                    "\"templateOptions\": {\n" +
                    "       \"type\": \"text\",\n" +
                    "       \"label\": \"Number\",\n" +
                    "       \"placeholder\": \"№\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"key\": \"full_name\",\n" +
                    "\"type\": \"input\",\n" +
                    "\"templateOptions\": {\n" +
                    "       \"type\": \"text\",\n" +
                    "       \"label\": \"Full name\",\n" +
                    "       \"placeholder\": \"Enter Full name\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"key\": \"date_birth\",\n" +
                    "\"type\": \"datepicker\",\n" +
                    "\"templateOptions\": {\n" +
                    "       \"label\": \"Date birth\",\n" +
                    "       \"type\": \"text\",\n" +
                    "       \"datepickerPopup\": \"yyyy-MMMM-dd\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"key\": \"sex\",\n" +
                    "\"type\": \"select\",\n" +
                    "\"templateOptions\": {\n" +
                    "       \"label\": \"Sex\",\n" +
                    "       \"options\": [\n" +
                    "               {\"name\": \"Male\", \"value\": \"1\"},\n" +
                    "               {\"name\": \"Female\", \"value\": \"0\"},\n" +
                    "               {\"name\": \"n/a\", \"value\": \"-1\"}\n" +
                    "       ]\n" +
                    "       }\n" +
                    "}\n" +
                    "]", "asds", "asds", Integer.toString(i)));
        }
        blankRepository.save(blanks);
        System.out.println("added blanks");
    }
}
