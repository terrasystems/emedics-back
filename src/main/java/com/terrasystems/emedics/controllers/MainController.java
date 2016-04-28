package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.RoleRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.services.UserFormsDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/")
public class MainController  {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    FormRepository formRepository;
    @Autowired
    UserFormsDashboardService userFormsDashboardService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String helloWorld() {
        return "Hello World";
    }

    @RequestMapping(value = "/rest/patients/create/{role}", method = RequestMethod.POST)
    @ResponseBody
    public String createPatient(@RequestBody Patient patient, @PathVariable String role) {
        User loadedUser = patient;
        Role newrole = new Role(role);
        newrole.setUser(loadedUser);
        try {
            userRepository.save(loadedUser);
            roleRepository.save(newrole);
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "succesfully created! (name = " + loadedUser.getUsername() + ")";
    }

    @RequestMapping(value = "/rest/doctors/create/{role}", method = RequestMethod.POST)
    @ResponseBody
    public String createDoctor(@RequestBody Doctor doctor, @PathVariable String role) {
        Doctor loadedUser = new Doctor(doctor.getUsername(), doctor.getPassword(), doctor.getEmail());
        loadedUser.setClinic("test");
        Role newrole = new Role(role);
        newrole.setUser(loadedUser);
        Set<Role> roles = new HashSet<>();
        roles.add(newrole);
        loadedUser.setRoles(roles);
        try {
            userRepository.save(loadedUser);
            //roleRepository.save(newrole);
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "succesfully created! (name = " + loadedUser.getRoles() + ")";
    }

    @RequestMapping(value = "/rest/test", method = RequestMethod.GET)
    @ResponseBody
    public String getPatientByName(@PathVariable String name) {
        return "hello";
    }

    @RequestMapping(value = "/rest/patient/form", method = RequestMethod.GET)
    @ResponseBody
    public String addForm() {
        Patient patient = new Patient("username", "email", "pass");
        Form form = formRepository.findOne(3l);
        List<Form> forms = new ArrayList<>();
        forms.add(form);
        patient.setForms(forms);
        userRepository.save(patient);
        return "Form added";
    }
    @RequestMapping(value = "/rest/forms", method = RequestMethod.POST)
    @ResponseBody
    public String addForms(@RequestBody String email) {
        userFormsDashboardService.generateFormsForUser(email);
        return "Form added";
    }

    @RequestMapping(value = "/rest/disc", method = RequestMethod.GET)
    @ResponseBody
    public String testDisc() {
        String disc;
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(email);
        return disc = user.getDiscriminatorValue();
    }
}