package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.dao.RoleRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

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
        User loadedUser = doctor;
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

    @RequestMapping(value = "/rest/patients/{name}", method = RequestMethod.GET)
    @ResponseBody
    public User getPatientByName(@PathVariable String name) {
        return userRepository.findByUsername(name);
    }
}
