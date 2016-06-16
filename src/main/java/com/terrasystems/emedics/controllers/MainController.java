package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.dao.*;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.TemplateEventDto;
import com.terrasystems.emedics.services.EventPatientService;
import com.terrasystems.emedics.services.LoaderService;
import com.terrasystems.emedics.services.MailService;
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
    DoctorRepository doctorRepository;
    @Autowired
    StuffRepository stuffRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    MailService mailService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventPatientService eventPatientService;
    @Autowired
    LoaderService loaderService;


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




    @RequestMapping(value = "/rest/disc", method = RequestMethod.GET)
    @ResponseBody
    public String testDisc() {
        String disc;
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(email);
        return disc = user.getDiscriminatorValue();
    }
    @RequestMapping(value = "/rest/doctors/{search}", method = RequestMethod.GET)
    @ResponseBody
    public void getDoctors(@PathVariable String search) {
        /*List<User> doctors;
        doctors =  doctorRepository.findByNameContainingOrTypeContainingOrEmailContaining(name,name,name);
        doctors.stream()
                .forEach(doctor -> System.out.println(doctor.getUsername()));*/
        List<Doctor> refs =  doctorRepository.findByIdIsNotAndNameContainingOrType_NameContainingOrEmailContaining("s",search,search,search);
        refs.forEach(ref -> System.out.println(ref.getName()));
    }

  /*  @RequestMapping(value = "/rest/doctors/", method = RequestMethod.GET)
    @ResponseBody
    public String createDoctor() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Jimmy Hendrix", "1234", "jimm@hend.com"));
        doctors.add(new Doctor("Brian Molko", "1234", "brian@molko.com"));
        doctors.add(new Doctor("Alan Mask", "1234", "Alan@Mask"));
        doctors.get(0).setType("terapeut");
        doctors.get(1).setType("oncologist");
        doctors.get(2).setType("surgeon");
        userRepository.save(doctors);

        return "Doctors Created";
    }*/
    @RequestMapping(value = "/rest/stuff", method = RequestMethod.GET)
    @ResponseBody
    public String createStuff() {
        List<Stuff> stuff = new ArrayList<>();
        stuff.add(new Stuff("Jenifer Lopez", "1234", "babki@gmail.com"));
        stuff.add(new Stuff("Nadegda Babkina", "1234", "babki@gmail.com"));
        stuff.add(new Stuff("Selin Page", "1234", "babki@gmail.com"));
        Organization org = organizationRepository.findOne("88f7144a-2976-41e5-a157-e0ccf9845468");
        org.setStuff(stuff);
        stuff.get(0).setOrganization(org);
        stuff.get(1).setOrganization(org);
        stuff.get(2).setOrganization(org);
        organizationRepository.save(org);
        stuffRepository.save(stuff);


        return "Stuff Created";
    }
    @RequestMapping(value = "/rest/stuff/admin/{search}", method = RequestMethod.GET)
    @ResponseBody
    public List<Stuff> getAdmins(@PathVariable String search) {



        return stuffRepository.findByNameContainingAndAdminIsTrueOrEmailContainingAndAdminIsTrue(search,search);
    }
    @RequestMapping(value = "/rest/velocity", method = RequestMethod.GET)
    @ResponseBody
    public String testVelocity() {
        User user = new User("testname","testpass","testmail");
        mailService.velocityTest(user);
        return "Sended";
    }

    /*@RequestMapping(value = "/rest/pats/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String testVelocity(@PathVariable String id) {
        List<String> strings = eventRepository.findTemplate_IdByPatient_Id(id);
        strings.forEach(System.out::println);
        return "Sended";
    }*/

    @RequestMapping(value = "/rest/pats/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<TemplateEventDto> testPatients(@PathVariable String id) {


        return eventPatientService.getPatientsEvents(id);
    }

    @RequestMapping(value = "/rest/init", method = RequestMethod.GET)
    @ResponseBody
    public String init() {
        loaderService.init();

        return "Initializtion";
    }

}
