package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.dto.RegisterDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;
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



    @Override
    public List<ReferenceDto> findAllReferencesByCriteria(String search) {
        User currentUser = userRepository.findByEmail(getPrincipals());
        Set<User> currentRefs = currentUser.getUserRef();
        ReferenceConverter converter = new ReferenceConverter();
        List<ReferenceDto> result = new ArrayList<>();

        if (currentUser.getDiscriminatorValue().equals("patient")) {
            List<Doctor> doctorRefs = doctorRepository.findByNameContainingOrTypeContainingOrEmailContaining(search,search,search).stream()
                    .filter(doctor -> !currentRefs.contains(doctor))
                    .collect(Collectors.toList());
            List<Stuff> stuffRef = stuffRepository.findByNameContainingAndAdminIsTrueOrEmailContainingAndAdminIsTrue(search,search).stream()
                    .filter((stuff -> !currentRefs.contains(stuff)))
                    .collect(Collectors.toList());
            result.addAll(converter.convertFromDoctors(doctorRefs));
            result.addAll(converter.convertFromStuff(stuffRef));

            return result;
        } else {
            List<Stuff> stuffRef = stuffRepository.findByNameContainingAndAdminIsTrueOrEmailContainingAndAdminIsTrue(search, search).stream()
                    .filter((stuff -> !currentRefs.contains(stuff)))
                    .collect(Collectors.toList());
            result.addAll(converter.convertFromStuff(stuffRef));

            return result;
        }
    }

    @Override
    @Transactional
    public StateDto addReferences(Set<String> references) {
        User current = userRepository.findByEmail(getPrincipals());
        if(current.getDiscriminatorValue().equals("patient")){
            List<User> refs = userRepository.findAll(references);
            Set<User> currentRefs = current.getUserRef();
            currentRefs.addAll(refs);
            current.setUserRef(currentRefs);
            userRepository.save(current);
            Doctor doctor = (Doctor) refs.get(0);
            List<Patient> patients = doctor.getPatients();
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
        Set<User> userRefs = current.getUserRef();
        List<String> refs = userRefs.stream()
                .map(user -> {
                    return user.getId();
                }).collect(Collectors.toList());
        List<User> refsList = (List<User>) userRepository.findAll(refs);

        return converter.convertFromUsers(refsList);
    }

    @Override
    public StateDto removeReferences(Set<String> references) throws Exception{
        User current = userRepository.findByEmail(getPrincipals());
        if(current.getDiscriminatorValue().equals("patient")){
            List<User> refs = userRepository.findAll(references);
            Set<User> currentRefs = current.getUserRef();
            currentRefs.removeAll(refs);
            current.setUserRef(currentRefs);
            userRepository.save(current);
            Doctor doctor = (Doctor) refs.get(0);
            List<Patient> patients = doctor.getPatients();
            patients.remove((Patient) current);
            userRepository.save(doctor);
            return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
        } else {
            List<User> refs = userRepository.findAll(references);
            Set<User> currentRefs = current.getUserRef();
            currentRefs.removeAll(refs);
            current.setUserRef(currentRefs);
            userRepository.save(current);
            return new StateDto(true, MessageEnums.MSG_SAVE_REFS.toString());
        }
    }

    @Override
    @Transactional
    public StateDto createReference(String email) {
        User current = userRepository.findByEmail(getPrincipals());
        RegisterDto registerDto = new RegisterDto();
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setUsername(email);
        registerDto.setUser(userDto);
        StateDto status = registrationService.registerUser(registerDto,"doc");
        if (status.isValue()){
            Doctor doctor = (Doctor) userRepository.findByEmail(email);
            current.getUserRef().add(doctor);
            doctor.getPatients().add((Patient) current);
            userRepository.save(current);
        }
        return status;
    }

    @Override
    public List<ReferenceDto> findMyRefs(String search, String type) {
        ReferenceConverter converter = new ReferenceConverter();
        User current = userRepository.findByEmail(getPrincipals());
        if(current.getDiscriminatorValue().equals("doctor")) {
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


}
