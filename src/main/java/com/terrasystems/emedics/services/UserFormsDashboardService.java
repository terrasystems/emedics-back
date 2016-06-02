package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.BlankRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserFormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Blank;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.UserForm;
import com.terrasystems.emedics.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFormsDashboardService {
    @Autowired
    UserFormRepository userFormRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    BlankRepository blankRepository;

    public void generateFormsForUser(String email) {
        User loadedUser = userRepository.findByEmail(email);
        List<UserForm> userUserForms = new ArrayList<>();
        List<Blank> blankList = (List<Blank>) blankRepository.findAll();
         blankList.stream().forEach((item) -> {
             UserForm userForm = new UserForm();
             userForm.setBlank(item);
             userForm.setUser(loadedUser);
             userForm.setData("{}");
             userUserForms.add(userForm);
         });
        loadedUser.setUserForms(userUserForms);
        userRepository.save(loadedUser);

    }

    public void init() {
        List<Template> templates = new ArrayList<>();
        for (int i = 0; i<3; i++) {

            templates.add(new Template("type1","category","{\"sections\":[\n" +
                    "            {\n" +
                    "               \"sectionA\": [\n" +
                    "                  {\n" +
                    "                     \"key\": \"number\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Number\",\n" +
                    "                        \"placeholder\": \"№\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"full_name\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Full name\",\n" +
                    "                        \"placeholder\": \"Enter Full name\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"date_birth\",\n" +
                    "                     \"type\": \"datepicker\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"label\": \"Date birth\",\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"datepickerPopup\": \"yyyy-MMMM-dd\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"sex\",\n" +
                    "                     \"type\": \"select\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"label\": \"Sex\",\n" +
                    "                        \"options\": [\n" +
                    "                           {\n" +
                    "                              \"name\": \"Male\",\n" +
                    "                              \"value\": \"1\"\n" +
                    "                           },\n" +
                    "                           {\n" +
                    "                              \"name\": \"Female\",\n" +
                    "                              \"value\": \"0\"\n" +
                    "                           },\n" +
                    "                           {\n" +
                    "                              \"name\": \"n/a\",\n" +
                    "                              \"value\": \"-1\"\n" +
                    "                           }\n" +
                    "                        ]\n" +
                    "                     }\n" +
                    "                  }\n" +
                    "               ]\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"sectionB\": [\n" +
                    "                  {\n" +
                    "                     \"key\": \"number\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Number\",\n" +
                    "                        \"placeholder\": \"№\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"name\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Name\",\n" +
                    "                        \"placeholder\": \"Enter name\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"second_name\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Second name\",\n" +
                    "                        \"placeholder\": \"Enter second name\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"allergies\",\n" +
                    "                     \"type\": \"textarea\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"label\": \"Allergies to medicines\",\n" +
                    "                        \"placeholder\": \"Allergies to medicines\",\n" +
                    "                        \"description\": \"\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"hiv_positive\",\n" +
                    "                     \"type\": \"checkbox\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"label\": \"HIV positive\"\n" +
                    "                     }\n" +
                    "                  }\n" +
                    "               ]\n" +
                    "            }\n" +
                    "         ]}", "desc", "name", Integer.toString(i)));
            templates.add(new Template("type2","category","{\"sections\":[\n" +
                    "            {\n" +
                    "               \"sectionA\": [\n" +
                    "                  {\n" +
                    "                     \"key\": \"number\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Number\",\n" +
                    "                        \"placeholder\": \"№\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"full_name\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Full name\",\n" +
                    "                        \"placeholder\": \"Enter Full name\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"date_birth\",\n" +
                    "                     \"type\": \"datepicker\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"label\": \"Date birth\",\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"datepickerPopup\": \"yyyy-MMMM-dd\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"sex\",\n" +
                    "                     \"type\": \"select\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"label\": \"Sex\",\n" +
                    "                        \"options\": [\n" +
                    "                           {\n" +
                    "                              \"name\": \"Male\",\n" +
                    "                              \"value\": \"1\"\n" +
                    "                           },\n" +
                    "                           {\n" +
                    "                              \"name\": \"Female\",\n" +
                    "                              \"value\": \"0\"\n" +
                    "                           },\n" +
                    "                           {\n" +
                    "                              \"name\": \"n/a\",\n" +
                    "                              \"value\": \"-1\"\n" +
                    "                           }\n" +
                    "                        ]\n" +
                    "                     }\n" +
                    "                  }\n" +
                    "               ]\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"sectionB\": [\n" +
                    "                  {\n" +
                    "                     \"key\": \"number\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Number\",\n" +
                    "                        \"placeholder\": \"№\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"name\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Name\",\n" +
                    "                        \"placeholder\": \"Enter name\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"second_name\",\n" +
                    "                     \"type\": \"input\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"type\": \"text\",\n" +
                    "                        \"label\": \"Second name\",\n" +
                    "                        \"placeholder\": \"Enter second name\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"allergies\",\n" +
                    "                     \"type\": \"textarea\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"label\": \"Allergies to medicines\",\n" +
                    "                        \"placeholder\": \"Allergies to medicines\",\n" +
                    "                        \"description\": \"\"\n" +
                    "                     }\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"key\": \"hiv_positive\",\n" +
                    "                     \"type\": \"checkbox\",\n" +
                    "                     \"templateOptions\": {\n" +
                    "                        \"label\": \"HIV positive\"\n" +
                    "                     }\n" +
                    "                  }\n" +
                    "               ]\n" +
                    "            }\n" +
                    "         ]}", "desc", "name", Integer.toString(i)));
        }
        templateRepository.save(templates);
        System.out.println("added blanks");
    }
}
