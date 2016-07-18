package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.dao.*;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.PatientCriteria;
import com.terrasystems.emedics.model.dto.TaskSearchCriteria;
import com.terrasystems.emedics.model.dto.TemplateEventDto;
import com.terrasystems.emedics.services.EventPatientService;
import com.terrasystems.emedics.services.LoaderService;
import com.terrasystems.emedics.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
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
    @Autowired
    PatientRepository patientRepository;


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
    /*@RequestMapping(value = "/rest/doctors/{search}", method = RequestMethod.GET)
    @ResponseBody
    public void getDoctors(@PathVariable String search) {

        List<Doctor> refs =  doctorRepository.findByIdIsNot(search);
        refs.forEach(ref -> System.out.println(ref.getName()));
    }*/

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
    /*@RequestMapping(value = "/rest/stuff", method = RequestMethod.GET)
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
    }*/
    /*@RequestMapping(value = "/rest/stuff/admin/{search}", method = RequestMethod.GET)
    @ResponseBody
    public List<Stuff> getAdmins(@PathVariable String search) {



        return stuffRepository.findByNameContainingAndAdminIsTrueOrEmailContainingAndAdminIsTrue(search,search);
    }*/
    @RequestMapping(value = "/rest/velocity", method = RequestMethod.GET)
    @ResponseBody
    public String testVelocity() {
        User user = new User("testname","testpass","testmail");
        mailService.velocityTest(user);
        return "Sended";
    }

    @RequestMapping(value = "/rest/pats/", method = RequestMethod.GET)
    @ResponseBody
    public String testPats() {
        PatientCriteria patientCriteria = new PatientCriteria();
        patientCriteria.setSearch("test");
        Doctor current = doctorRepository.findOne("47521352-aac3-4db8-850f-08b3dd93d340");
        List<Patient> patients = patientRepository.findAll(Specifications.<Patient>where((r, q, b) -> {
            Subquery<Patient> sq = q.subquery(Patient.class);
            Root<Doctor> doctor = sq.from(Doctor.class);
            Join<Doctor, Patient> sqPat = doctor.join("patients");
            System.out.println("dddd");
            sq.select(sqPat.get("id")).where(b.equal(doctor.get("id"), current.getId()));
            return b.in(r).value(sq);
        }));
        patients.forEach(patient -> System.out.println(patient.getName()));
        return "Sended";
    }

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

    @RequestMapping(value = "/rest/spec" , method = RequestMethod.POST)
    @ResponseBody
    public String spec(@RequestBody TaskSearchCriteria criteria) {
       /* EventSpecification specification = new EventSpecification(new TaskSearchCriteria(12,"a","a","a"));
        List<Event> events = eventRepository.findAll(Specifications.<Event>where((r,q,b) -> {
            return null b.like(r.<User>get("fromUser").<String>get("name"), "%a%");
        })
        .and((r,q,b) -> {
            return nullb.like(r.<Template>get("template").<String>get("name"), "%"+ null +"%");

        }));
        EventSpecification specification = new EventSpecification(criteria);
        List<Event> events = eventRepository.findAll(specification);
        events.forEach(event -> {System.out.println(event.getFromUser().getName());});*/
        return "spec";
    }

}
