package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.PatientRepository;
import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import com.terrasystems.emedics.security.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "patientReferenceService")
public class PatientReferenceServiceImpl implements CurrentUserService, ReferenceService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    StuffRepository stuffRepository;
    @Autowired
    RegistrationService registrationService;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ReferenceCreateService referenceCreateService;



    @Override
    public List<ReferenceDto> findMyReferencesByCriteria(String search) {
        User currentUser = userRepository.findByEmail(getPrincipals());
        if (currentUser.getDiscriminatorValue().equals("patient")) {
            return findPatientReferencesByCriteria(search);
        } else if (currentUser.getDiscriminatorValue().equals("doctor")) {
            return findDoctorsReferencesByCriteria(search);
        } else return findStuffReferencesByCriteria(search);
    }

    @Override
    public List<ReferenceDto> findAllReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        User current = userRepository.findByEmail(getPrincipals());
        List<ReferenceDto> refs = new ArrayList<>();
        if (current.getDiscriminatorValue().equals("patient")) {
            refs.addAll(converter.convertFromDoctors(doctorRepository.findByIdIsNotAndNameContainingOrType_NameContainingOrEmailContaining(current.getId(),search,search,search)));
            refs.addAll(converter.convertFromStuff(stuffRepository.findByIdIsNotAndDoctor_NameContainingAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingAndDoctor_AdminIsTrue(current.getId(), search,search)));

            return refs;
        } else {
            refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndNameContainingOrEmailContaining(current.getId(),search,search)));
            return refs;
        }
    }


    @Override
    @Transactional
    public StateDto addReferences(Set<String> references) {
        User current = userRepository.findByEmail(getPrincipals());
        if (current.getDiscriminatorValue().equals("patient")) {
            List<User> refs = userRepository.findAll(references);
            Set<User> currentRefs = current.getUserRef();
            currentRefs.addAll(refs);
            current.setUserRef(currentRefs);
            Doctor doctor = (Doctor) refs.get(0);
            doctor.getUserRef().add(current);
            List<Patient> patients = doctor.getPatients();
            userRepository.save(current);
            if(patients.contains((Patient) current)){
                return new StateDto(false, "This references exist already");
            } else {
                patients.add((Patient) current);
                userRepository.save(doctor);
                return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
            }
        } else {
            List<User> refs = userRepository.findAll(references);
            Set<User> currentRefs = current.getUserRef();
            currentRefs.addAll(refs);
            current.setUserRef(currentRefs);
            userRepository.save(current);
            return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
        }

    }

    @Override
    public Iterable<ReferenceDto> getAllReferences() {
        ReferenceConverter converter = new ReferenceConverter();
        User current = userRepository.findByEmail(getPrincipals());
        /*Set<User> userRefs = current.getUserRef();
        List<String> refs = userRefs.stream()
                .map(user -> {
                    return user.getId();
                }).collect(Collectors.toList());
        List<User> refsList = (List<User>) userRepository.findAll(refs);*/

        return converter.convertFromUsers(current.getUserRef());
    }

    @Override
    @Transactional
    public StateDto removeReferences(Set<String> references) throws Exception {
        User current = userRepository.findByEmail(getPrincipals());
        if(current.getDiscriminatorValue().equals("patient")){
            List<User> refs = userRepository.findAll(references);
            Set<User> currentRefs = current.getUserRef();
            currentRefs.removeAll(refs);
            current.setUserRef(currentRefs);
            userRepository.save(current);
            Doctor doctor = (Doctor) refs.get(0);
            List<Patient> patients = doctor.getPatients();
            patients.remove(current);
            userRepository.save(doctor);
            return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
        } else {
            List<User> refs = userRepository.findAll(references);
            Set<User> currentRefs = current.getUserRef();
            User user = refs.get(0);
            currentRefs.removeAll(refs);
            current.setUserRef(currentRefs);
            user.getUserRef().remove(current);
            return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
        }
    }

    @Override
    @Transactional
    public String createReference(ReferenceCreateRequest request) {
        User current = userRepository.findByEmail(getPrincipals());
        if (current.getDiscriminatorValue().equals("patient")) {
            Doctor doctor = referenceCreateService.createDoctor(request);
            if (doctor != null) {
                current.getUserRef().add(doctor);
                doctor.getPatients().add((Patient) current);
                return current.getId();
            } else return null;
        } else if (current.getDiscriminatorValue().equals("doctor")) {
            if (request.getType().equals("pat")) {
                Patient patient = referenceCreateService.createPatient(request);
                if (patient != null) {
                    Doctor currentDoctor = (Doctor) userRepository.findByEmail(getPrincipals());
                    currentDoctor.getPatients().add(patient);
                    currentDoctor.getUserRef().add(patient);
                    patient.getUserRef().add(currentDoctor);
                    patient.getDoctors().add(currentDoctor);
                    userRepository.save(currentDoctor);
                    userRepository.save(patient);
                    return currentDoctor.getId();
                } else return null;
            } else {
                Doctor doctor = referenceCreateService.createDoctor(request);
                if (doctor != null) {
                    Doctor currentDoctor = (Doctor) userRepository.findByEmail(getPrincipals());
                    currentDoctor.getUserRef().add(doctor);
                    doctor.getUserRef().add(currentDoctor);
                    userRepository.save(doctor);
                    userRepository.save(currentDoctor);
                    return currentDoctor.getId();
                } else return null;
            }
        } else return null;
    }

    @Override
    public List<ReferenceDto> findMyRefs(String search, String type) {
        ReferenceConverter converter = new ReferenceConverter();
        User current = userRepository.findByEmail(getPrincipals());
        if (current.getDiscriminatorValue().equals("doctor")) {
            Doctor doctor = (Doctor) current;
            List<ReferenceDto> myRefs = new ArrayList<>();
            if (!type.equals("pat")) {
                myRefs.addAll(converter.convertFromPatients(doctor.getPatients()));
            } else {
                myRefs.addAll(converter.convertFromPatients(doctor.getPatients()));
                myRefs.addAll((Collection<? extends ReferenceDto>) getAllReferences());
            }
            return myRefs.stream().filter(referenceDto -> {
                return referenceDto.getName().contains(search);
            }).collect(Collectors.toList());
        } else if (current.getDiscriminatorValue().equals("patient")){
            Patient patient = (Patient) current;
            List<ReferenceDto> myRefs = new ArrayList<>();
            myRefs.addAll((Collection<? extends ReferenceDto>) getAllReferences());
            return myRefs;
        } else {
            return null;
        }
    }

    private List<ReferenceDto> findDoctorRefs(String search, String type) {
        ReferenceConverter converter = new ReferenceConverter();
        Doctor doctor = (Doctor) userRepository.findByEmail(getPrincipals());
        List<ReferenceDto> myRefs = new ArrayList<>();
        if (!type.equals("pat")) {
            myRefs.addAll(converter.convertFromPatients(doctor.getPatients()));
        } else {
            myRefs.addAll(converter.convertFromPatients(doctor.getPatients()));
            myRefs.addAll((Collection<? extends ReferenceDto>) getAllReferences());
        }
        return myRefs.stream().filter(referenceDto -> {
            return referenceDto.getName().contains(search);
        }).collect(Collectors.toList());
    }

    private List<ReferenceDto> findDoctorsReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = current.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        List<Stuff> stuffRef = stuffRepository.findByIdIsNotAndDoctor_NameContainingAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingAndDoctor_AdminIsTrue(current.getId(),search, search).stream()
                .filter((stuff -> !currentRefs.contains(stuff)))
                .collect(Collectors.toList());
        List<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingOrIdIsNotAndType_NameContainingOrIdIsNotAndEmailContaining(current.getId(),search, current.getId(), search, current.getId(),search).stream()
                .filter(doctor -> !currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<Patient> patientsRefs = patientRepository.findByIdIsNotAndNameContainingOrEmailContaining(current.getId(),search,search).stream()
                .filter(patient -> !currentRefs.contains(patient))
                .collect(Collectors.toList());
        refs.addAll(converter.convertFromStuff(stuffRef));
        refs.addAll(converter.convertFromDoctors(doctorsRefs));
        refs.addAll(converter.convertFromPatients(patientsRefs));
        return refs;
    }

    private List<ReferenceDto> findStuffReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        Stuff current = (Stuff) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = current.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        List<Patient> patientsRefs = patientRepository.findByIdIsNotAndNameContainingOrEmailContaining(current.getId(),search,search).stream()
                .filter(patient -> !currentRefs.contains(patient))
                .collect(Collectors.toList());
        List<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingOrType_NameContainingOrEmailContaining(current.getId(),search,search,search).stream()
                .filter(doctor -> !currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<Stuff> stuffRef = stuffRepository.findByIdIsNotAndDoctor_NameContainingAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingAndDoctor_AdminIsTrue(current.getId(),search, search).stream()
                .filter((stuff -> !currentRefs.contains(stuff)))
                .collect(Collectors.toList());

        refs.addAll(converter.convertFromStuff(stuffRef));
        refs.addAll(converter.convertFromDoctors(doctorsRefs));
        refs.addAll(converter.convertFromPatients(patientsRefs));
        return refs;
    }

    private List<ReferenceDto> findPatientReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        Patient current = (Patient) userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = current.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        List<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingOrType_NameContainingOrEmailContaining(current.getId(),search,search,search).stream()
                .filter(doctor -> !currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<Stuff> stuffRef = stuffRepository.findByIdIsNotAndDoctor_NameContainingAndDoctor_AdminIsTrueOrDoctor_Type_NameContainingAndDoctor_AdminIsTrue(current.getId(),search, search).stream()
                .filter((stuff -> !currentRefs.contains(stuff)))
                .collect(Collectors.toList());
        refs.addAll(converter.convertFromDoctors(doctorsRefs));
        refs.addAll(converter.convertFromStuff(stuffRef));
        return refs;
    }


}
